package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.inputs.EvaluationCreationInput;
import br.edu.ufcg.genus.inputs.EvaluationEditInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.EvaluationRepository;

@Service
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@Autowired
	private StudentSubjectService studentSubjectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
	public Evaluation findEvaluation(Long id) {
		return evaluationRepository.findById(id)
				.orElseThrow(() -> new InvalidIDException("Evaluation with passed ID was not found", id));
	}
	
	public Evaluation createEvaluation(EvaluationCreationInput input, User user) {
		checkEvaluationCreation(input, user);
		StudentSubject studentSubject = studentSubjectService.findStudentSubject(input.getUserId(), input.getSubjectId());
		
		Evaluation eval = new Evaluation(input.getName(), input.getResult(), input.getWeight(), studentSubject);
		evaluationRepository.save(eval);
		studentSubject.addEvaluation(eval);
		studentSubjectService.saveStudentSubject(studentSubject);
		return eval;
	}
	
	public Evaluation editEvaluation(EvaluationEditInput input, User user) {
		Evaluation eval = findEvaluation(input.getEvaluationId());
		checkEvaluationEdit(eval, user);
		updateStudentSubject(input, eval);
		eval.setName(input.getName());
		eval.setResult(input.getResult());
		eval.setWeight(input.getWeight());
		Evaluation updated = evaluationRepository.save(eval);
		return updated;
	}
	
	private void updateStudentSubject(EvaluationEditInput input, Evaluation eval) {
		StudentSubject studentSubject = eval.getStudentSubject();
		double newAvarage = studentSubject.getAvarage() - (eval.getResult() * eval.getWeight());
		newAvarage += input.getWeight() * input.getResult();
		studentSubject.setAvarage(newAvarage);
		studentSubjectService.saveStudentSubject(eval.getStudentSubject());
	}
	
	//put this on the permission checker
	private void checkEvaluationCreation(EvaluationCreationInput input, User user) {
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		User student = userService.findUserById(input.getUserId());
		if (!subject.findStudents().contains(student) || !subject.getTeachers().contains(user)) {
			throw new NotAuthorizedException("Logged user isn't a teacher to the subject or the user being evaluated isn't a student of this subject");
		}
	}
	
	private void checkEvaluationEdit(Evaluation eval, User user) {
		Subject subject = eval.getStudentSubject().getSubject();
		if(!subject.getTeachers().contains(user)) {
			throw new NotAuthorizedException("Logged user isn't a teacher to the subject");
		}
	}
	
	

}
