package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.inputs.EvaluationCreationInput;
import br.edu.ufcg.genus.models.Evaluation;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.StudentSubjectId;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.EvaluationRepository;
import br.edu.ufcg.genus.repositories.StudentSubjectRepository;

@Service
public class EvaluationService {
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@Autowired
	private StudentSubjectRepository studentSubjectRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
	public Evaluation createEvaluation(EvaluationCreationInput input, User user) {
		checkEvaluationCreation(input, user);
		
		StudentSubjectId ssid = new StudentSubjectId(input.getUserId(), input.getSubjectId());
		StudentSubject studentSubject = studentSubjectRepository.findById(ssid)
				.orElseThrow(() -> new InvalidIDException("StudentSubject with passed ID was not found", ssid));
		
		Evaluation eval = new Evaluation(input.getName(), input.getResult(), input.getWeight(), studentSubject);
		studentSubject.addEvaluation(eval);
		evaluationRepository.save(eval);
		studentSubjectRepository.save(studentSubject);
		return eval;
	}
	
	private void checkEvaluationCreation(EvaluationCreationInput input, User user) {
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		User student = userService.findUserById(input.getUserId());
		if (!subject.findStudents().contains(student) || !subject.getTeachers().contains(user)) {
			throw new NotAuthorizedException("Logged user isn't a teacher to the subject or the user being evaluated isn't a student of this subject");
		}
	}

}