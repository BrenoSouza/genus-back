package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.CreateEvaluationResultInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.models.EvaluationResult;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.EvaluationResultRepository;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class EvaluationResultService {
	
	@Autowired
	private EvaluationResultRepository evaluationResultRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EvaluationService evaluationService;
	
	
	public EvaluationResult findEvaluationResult(Long id) {
		return this.evaluationResultRepository.findById(id)
				.orElseThrow(() -> new InvalidIDException("EvaluationResult with passed ID was not found", id));
	}
	
	public List<EvaluationResult> createEvaluationResults(Collection<CreateEvaluationResultInput> inputs, User user) {
		List<EvaluationResult> results = new ArrayList<>();
		Set<User> usersToUpdate = new HashSet<>();
		Set<Evaluation> evaluationsToUpdate = new HashSet<>();
		User student;
		Evaluation eval;
		EvaluationResult result;
		for(CreateEvaluationResultInput input : inputs) {
			student = this.userService.findUserById(input.getStudentId());
			eval = this.evaluationService.findEvaluation(input.getEvaluationId());
			result = createEvaluationResult(student, eval, input.getResult(), user);
			if (result != null) {
				results.add(result);
				usersToUpdate.add(student);
				evaluationsToUpdate.add(eval);
			}
		}
		this.evaluationResultRepository.saveAll(results);
		this.userService.saveUsers(usersToUpdate);
		this.evaluationService.saveEvaluations(evaluationsToUpdate);
		return results;
	}

	private EvaluationResult createEvaluationResult(User student, Evaluation eval, Double result, User user) {
		PermissionChecker.checkEvaluationPermission(eval.getSubject(), user);
		PermissionChecker.checkIsSubjectStudent(eval.getSubject(), student);
		EvaluationResult evaluationResult = new EvaluationResult(eval, student, result);
		eval.addEvaluationResult(evaluationResult);
		student.addEvaluationResult(evaluationResult);
		return evaluationResult;
	}
	
	public EvaluationResult editEvaluationResult(Long resultId, Double newResult, User user) {
		EvaluationResult eval = findEvaluationResult(resultId);
		PermissionChecker.checkEvaluationPermission(eval.getEvaluation().getSubject(), user);
		eval.setResult(newResult);
		this.evaluationResultRepository.save(eval);
		return eval;
	}
	
	

}
