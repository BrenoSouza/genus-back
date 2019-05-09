package br.edu.ufcg.genus.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.EvaluationCreationInput;
import br.edu.ufcg.genus.inputs.EvaluationEditInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.EvaluationRepository;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
@Transactional(readOnly = true)
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private EvaluationResultService evaluationResultService;
	
	public Evaluation findEvaluation(Long id) {
		return evaluationRepository.findById(id)
				.orElseThrow(() -> new InvalidIDException("Evaluation with passed ID was not found", id));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveEvaluations(Iterable<Evaluation> evaluations) {
		this.evaluationRepository.saveAll(evaluations);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Evaluation createEvaluation(EvaluationCreationInput input ,User user) {
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		PermissionChecker.checkEvaluationPermission(subject, user);
		Evaluation eval = new Evaluation(input.getName(), input.getWeight(), subject);
		evaluationRepository.save(eval);
		subject.addEvaluation(eval);
		subjectService.saveSubjectInRepository(subject);
		evaluationResultService.fillEvaluation(eval, input.getResultInputs(), user);
		return findEvaluation(eval.getId());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Evaluation editEvaluation(EvaluationEditInput input, User user) {
		Evaluation eval = findEvaluation(input.getEvaluationId());
		PermissionChecker.checkEvaluationPermission(eval.getSubject(), user);
		eval.setName(input.getName());
		eval.setWeight(input.getWeight());
		Evaluation updated = evaluationRepository.save(eval);
		return updated;
	}
}
