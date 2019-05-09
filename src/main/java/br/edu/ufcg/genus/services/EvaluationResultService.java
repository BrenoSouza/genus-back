package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.CreateEvaluationResultInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.models.EvaluationResult;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.EvaluationResultRepository;
import br.edu.ufcg.genus.update_inputs.UpdateEvaluationResult;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
@Transactional(readOnly = true)
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private EvaluationResult createEvaluationResult(User student, Evaluation eval, Double result, User user) {
		PermissionChecker.checkEvaluationPermission(eval.getSubject(), user);
		PermissionChecker.checkIsSubjectStudent(eval.getSubject(), student);
		EvaluationResult evaluationResult = new EvaluationResult(eval, student, result);
		eval.addEvaluationResult(evaluationResult);
		student.addEvaluationResult(evaluationResult);
		return evaluationResult;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Collection<EvaluationResult> updateEvaluationResults(Collection<UpdateEvaluationResult> inputs, User user) {
		List<EvaluationResult> results = new ArrayList<>();
		for(UpdateEvaluationResult input : inputs) {
			results.add(updateEvaluationResult(input, user));
		}
		return results;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private EvaluationResult updateEvaluationResult(UpdateEvaluationResult input, User user) {
		EvaluationResult eval = findEvaluationResult(input.getResultId());
		PermissionChecker.checkEvaluationPermission(eval.getEvaluation().getSubject(), user);
		eval.setResult(input.getNewResult());
		this.evaluationResultRepository.save(eval);
		return eval;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void fillEvaluation(Evaluation eval, Iterable<CreateEvaluationResultInput> iterable, User user) {
		List<CreateEvaluationResultInput> inputs = new ArrayList<>();
		for(CreateEvaluationResultInput idlessInput : iterable) {
			inputs.add(new CreateEvaluationResultInput(eval.getId(), idlessInput.getStudentId(), idlessInput.getResult()));
		}
		createEvaluationResults(inputs, user);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void addSubjectEvaluationResults(User student, Subject subject, User user) {
		List<EvaluationResult> results = new ArrayList<>();
		for(Evaluation eval : subject.getEvaluations()) {
			results.add(createEvaluationResult(student, eval, 0.0, user));
		}
		this.evaluationResultRepository.saveAll(results);
		
	}
	
	

}
