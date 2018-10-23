package br.edu.ufcg.genus.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.inputs.GradeCreationInput;
import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserInstitution;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.GradeRepository;
import br.edu.ufcg.genus.repositories.InstitutionRepository;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.repositories.UserInstitutionRepository;
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
	private GradeRepository gradeRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		CreateUserInput adminInput = new CreateUserInput("administrador", "admin@gmail.com", "123456");
		CreateUserInput prof1Input = new CreateUserInput("professor1", "prof1@gmail.com", "123456");
		CreateUserInput prof2Input = new CreateUserInput("professor2", "prof2@gmail.com", "123456");
		CreateUserInput prof3Input = new CreateUserInput("professor3", "prof3@gmail.com", "123456");

		User admin = userService.createUser(adminInput);
		User prof1 = userService.createUser(prof1Input);
		User prof2 = userService.createUser(prof2Input);
		User prof3 = userService.createUser(prof3Input);

		Institution institution = new Institution("Escola", "Rua qualquer", "838888888", "escola@gmail.com" );
		
		institutionRepository.save(institution);

		UserInstitution userInstitution = institution.addUser(admin, UserRole.ADMIN);
		
		userInstitutionRepository.save(userInstitution);
		institutionRepository.save(institution);
		userService.saveUserInRepository(admin);

		institutionService.addUserToInstitution(prof1, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(prof2, institution, UserRole.TEACHER);
		
		//Grade grade = gradeService.createGrade(new GradeCreationInput("1 Serie", institution.getId()));
		//Subject subject = subjectService.createSubject(new SubjectCreationInput("Matematica", grade.getId()));
		
		Grade grade = new Grade("Serie 1", institution);
		gradeRepository.save(grade);
		Subject subject = new Subject(grade, "Matematica");
		subjectRepository.save(subject);
		
		userService.addTeacher(subject.getId(), prof1.getId());
		
		

	}

}