package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.EvaluationCreationInput;
import br.edu.ufcg.genus.inputs.EvaluationEditInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.EvaluationRepository;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	public Evaluation findEvaluation(Long id) {
		return evaluationRepository.findById(id)
				.orElseThrow(() -> new InvalidIDException("Evaluation with passed ID was not found", id));
	}
	
	public void saveEvaluations(Iterable<Evaluation> evaluations) {
		this.evaluationRepository.saveAll(evaluations);
	}
	
	public Evaluation createEvaluation(EvaluationCreationInput input, User user) {
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		PermissionChecker.checkEvaluationPermission(subject, user);
		Evaluation eval = new Evaluation(input.getName(), input.getWeight(), subject);
		evaluationRepository.save(eval);
		subject.addEvaluation(eval);
		subjectService.saveSubjectInRepository(subject);
		return eval;
	}
	
	public Evaluation editEvaluation(EvaluationEditInput input, User user) {
		Evaluation eval = findEvaluation(input.getEvaluationId());
		PermissionChecker.checkEvaluationPermission(eval.getSubject(), user);
		eval.setName(input.getName());
		eval.setWeight(input.getWeight());
		Evaluation updated = evaluationRepository.save(eval);
		return updated;
	}
}
