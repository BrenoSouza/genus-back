package br.edu.ufcg.genus.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.inputs.EvaluationCreationInput;
import br.edu.ufcg.genus.inputs.GradeCreationInput;
import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserInstitution;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.InstitutionRepository;
import br.edu.ufcg.genus.repositories.UserInstitutionRepository;
import br.edu.ufcg.genus.services.EvaluationService;
import br.edu.ufcg.genus.services.GradeService;
import br.edu.ufcg.genus.services.InstitutionService;
import br.edu.ufcg.genus.services.SubjectService;
import br.edu.ufcg.genus.services.UserService;


@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private InstitutionService institutionService;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private UserInstitutionRepository userInstitutionRepository;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private SubjectService subjectService;	
	
	@Autowired
	private EvaluationService evaluationService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		CreateUserInput adminInput = new CreateUserInput("administrador", "admin@gmail.com", "123456");
		CreateUserInput prof1Input = new CreateUserInput("professor1", "prof1@gmail.com", "123456");
		CreateUserInput prof2Input = new CreateUserInput("professor2", "prof2@gmail.com", "123456");
		CreateUserInput prof3Input = new CreateUserInput("professor3", "prof3@gmail.com", "123456");
		CreateUserInput stud1Input = new CreateUserInput("aluno1", "alu1@gmail.com", "123456");
		CreateUserInput stud2Input = new CreateUserInput("aluno2", "alu2@gmail.com", "123456");

		User admin = userService.createUser(adminInput);
		User prof1 = userService.createUser(prof1Input);
		User prof2 = userService.createUser(prof2Input);
		User prof3 = userService.createUser(prof3Input);
		User stud1 = userService.createUser(stud1Input);
		User stud2 = userService.createUser(stud2Input);

		Institution institution = new Institution("Escola", "Rua qualquer", "838888888", "escola@gmail.com" );
		
		institutionRepository.save(institution);

		UserInstitution userInstitution = institution.addUser(admin, UserRole.ADMIN);
		
		userInstitutionRepository.save(userInstitution);
		institutionRepository.save(institution);
		userService.saveUserInRepository(admin);

		institutionService.addUserToInstitution(prof1, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(prof2, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(stud2, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud1, institution, UserRole.STUDENT);

		Grade grade1 = gradeService.createGrade(new GradeCreationInput("1 Serie", institution.getId()), admin);
		Subject subject = subjectService.createSubject(new SubjectCreationInput("Matematica", grade1.getId()), admin);
		Subject subject2 = subjectService.createSubject(new SubjectCreationInput("Portugues", grade1.getId()), admin);
		
		subjectService.addTeacher(subject.getId(), prof1.getId(), admin);
		//subjectService.addTeacher(subject.getId(), prof2.getId(), admin);
		subjectService.addTeacher(subject2.getId(), prof1.getId(), admin);
		subjectService.addTeacher(subject2.getId(), prof2.getId(), admin);
		
		subjectService.addStudent(subject.getId(), stud1.getId(), admin);
		subjectService.addStudent(subject2.getId(), stud1.getId(), admin);
		subjectService.addStudent(subject2.getId(), stud2.getId(), admin);
		
		EvaluationCreationInput ceInput1 = new EvaluationCreationInput(new Long(5), new Long(9), "Prova 1", 6.0, 4.0);
		EvaluationCreationInput ceInput2 = new EvaluationCreationInput(new Long(5), new Long(9), "Prova 2", 9.0, 6.0);
		evaluationService.createEvaluation(ceInput1, prof1);
		evaluationService.createEvaluation(ceInput2, prof1);
		
		Grade grade2 = gradeService.createGrade(new GradeCreationInput("2 Serie", institution.getId()), admin);
		Subject subject3 = subjectService.createSubject(new SubjectCreationInput("Geometria", grade2.getId()), admin);
		
	}

}