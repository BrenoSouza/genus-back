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
import br.edu.ufcg.genus.repositories.InstitutionRepository;
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
	
	/*@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private DiscussionService discussionService;
	
	@Autowired
	private ReplyService replyService;*/

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		CreateUserInput adminInput = new CreateUserInput("Julio Silva", "julio_silva@genus.com", "123456");
		CreateUserInput prof1Input = new CreateUserInput("Samuel Gomes", "samuel_gomes@genus.com", "123456");
		CreateUserInput prof2Input = new CreateUserInput("Bianca Alves", "bianca_alves@genus.com", "123456");
		CreateUserInput prof3Input = new CreateUserInput("Marina Lima", "marina_lima@genus.com", "123456");
		CreateUserInput stud1Input = new CreateUserInput("Thiago Pinto", "thiago_pinto@genus.com", "123456");
		CreateUserInput stud2Input = new CreateUserInput("Amanda Martins", "amanda_martins@genus.com", "123456");

		User admin = userService.createUser(adminInput);
		User prof1 = userService.createUser(prof1Input);
		User prof2 = userService.createUser(prof2Input);
		User prof3 = userService.createUser(prof3Input);
		User stud1 = userService.createUser(stud1Input);
		User stud2 = userService.createUser(stud2Input);
		
		
		Institution institution = new Institution("Escola Álvaro Cardoso", "Rua Julio Santos", "8333229851", "escola_alvaro_cardoso@genus.com" );
		
		institutionRepository.save(institution);

		UserInstitution userInstitution = institution.addUser(admin, UserRole.ADMIN);
		
		userInstitutionRepository.save(userInstitution);
		institutionRepository.save(institution);
		userService.saveUserInRepository(admin);

		institutionService.addUserToInstitution(prof1, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(prof2, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(prof3, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(stud2, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud1, institution, UserRole.STUDENT);

		Grade grade1 = gradeService.createGrade(new GradeCreationInput("1ª Série", institution.getId()), admin);
		Subject subject = subjectService.createSubject(new SubjectCreationInput("Matemática", grade1.getId()), admin);
		Subject subject2 = subjectService.createSubject(new SubjectCreationInput("Português", grade1.getId()), admin);
		
		subjectService.addTeacher(subject.getId(), prof1.getId(), admin);
		subjectService.addTeacher(subject2.getId(), prof1.getId(), admin);
		subjectService.addTeacher(subject2.getId(), prof2.getId(), admin);
		
		subjectService.addStudent(subject.getId(), stud1.getId(), admin);
		subjectService.addStudent(subject2.getId(), stud1.getId(), admin);
		subjectService.addStudent(subject2.getId(), stud2.getId(), admin);
		
		Grade grade2 = gradeService.createGrade(new GradeCreationInput("2ª Série", institution.getId()), admin);
		Subject subject3 = subjectService.createSubject(new SubjectCreationInput("Geometria", grade2.getId()), admin);
		
		
		CreateUserInput admin2Input = new CreateUserInput("Lucia Cardoso", "lucia_cardoso@genus.com", "123456");
		CreateUserInput prof4Input = new CreateUserInput("Giovana Azevedo", "giovana_azevedo@genus.com", "123456");
		CreateUserInput prof5Input = new CreateUserInput("Maria Barbosa", "maria_barbosa@genus.com", "123456");
		CreateUserInput stud3Input = new CreateUserInput("Pedro Rocha", "pedro_rocha@genus.com", "123456");
		CreateUserInput stud4Input = new CreateUserInput("Carlos Lima", "carlos_lima@genus.com", "123456");
		CreateUserInput stud5Input = new CreateUserInput("Luiz Azevedo", "luiz_azevedo@genus.com", "123456");
		CreateUserInput stud6Input = new CreateUserInput("Camila Santos", "camila_santos@genus.com", "123456");
		CreateUserInput stud7Input = new CreateUserInput("Jose Araujo", "jose_araujo@genus.com", "123456");
		CreateUserInput stud8Input = new CreateUserInput("Clara Oliveira", "clara_oliveira@genus.com", "123456");
		CreateUserInput stud9Input = new CreateUserInput("Bruna Ferreira", "bruna_ferreira@genus.com", "123456");
		CreateUserInput stud10Input = new CreateUserInput("Aline Correia", "aline_correia@genus.com", "123456");
		
		User admin2 = userService.createUser(admin2Input);
		User prof4 = userService.createUser(prof4Input);
		User prof5 = userService.createUser(prof5Input);
		User stud3 = userService.createUser(stud3Input);
		User stud4 = userService.createUser(stud4Input);
		User stud5 = userService.createUser(stud5Input);
		User stud6 = userService.createUser(stud6Input);
		User stud7 = userService.createUser(stud7Input);
		User stud8 = userService.createUser(stud8Input);
		User stud9 = userService.createUser(stud9Input);
		User stud10 = userService.createUser(stud10Input);
		
		institutionService.addUserToInstitution(admin2, institution, UserRole.ADMIN);
		institutionService.addUserToInstitution(prof4, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(prof5, institution, UserRole.TEACHER);
		institutionService.addUserToInstitution(stud3, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud4, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud5, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud6, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud7, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud8, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud9, institution, UserRole.STUDENT);
		institutionService.addUserToInstitution(stud10, institution, UserRole.STUDENT);
		
		//grade1
		Subject subject4 = subjectService.createSubject(new SubjectCreationInput("História", grade1.getId()), admin);
		Subject subject5 = subjectService.createSubject(new SubjectCreationInput("Geografia", grade1.getId()), admin);
		Subject subject6 = subjectService.createSubject(new SubjectCreationInput("Ciência", grade1.getId()), admin);
		
		//grade2
		Subject subject7 = subjectService.createSubject(new SubjectCreationInput("Álgebra", grade2.getId()), admin);
		Subject subject8 = subjectService.createSubject(new SubjectCreationInput("Português", grade2.getId()), admin);
		Subject subject9 = subjectService.createSubject(new SubjectCreationInput("História", grade2.getId()), admin);
		Subject subject10 = subjectService.createSubject(new SubjectCreationInput("Geografia", grade2.getId()), admin);
		Subject subject11 = subjectService.createSubject(new SubjectCreationInput("Ciência", grade2.getId()), admin);
		
		//grade3
		/*Grade grade3 = gradeService.createGrade(new GradeCreationInput("3ª Série", institution.getId()), admin);
		Subject subject12 = subjectService.createSubject(new SubjectCreationInput("Geometria", grade3.getId()), admin);
		Subject subject13 = subjectService.createSubject(new SubjectCreationInput("Álgebra", grade3.getId()), admin);
		Subject subject14 = subjectService.createSubject(new SubjectCreationInput("Gramática", grade3.getId()), admin);
		Subject subject15 = subjectService.createSubject(new SubjectCreationInput("História", grade3.getId()), admin);
		Subject subject16 = subjectService.createSubject(new SubjectCreationInput("Geografia", grade3.getId()), admin);
		Subject subject17 = subjectService.createSubject(new SubjectCreationInput("Ciência", grade3.getId()), admin);
		Subject subject18 = subjectService.createSubject(new SubjectCreationInput("Literatura", grade3.getId()), admin);
		
		//grade4
		Grade grade4 = gradeService.createGrade(new GradeCreationInput("4ª Série", institution.getId()), admin);
		Subject subject19 = subjectService.createSubject(new SubjectCreationInput("Geometria", grade4.getId()), admin);
		Subject subject20 = subjectService.createSubject(new SubjectCreationInput("Álgebra", grade4.getId()), admin);
		Subject subject21 = subjectService.createSubject(new SubjectCreationInput("Gramática", grade4.getId()), admin);
		Subject subject22 = subjectService.createSubject(new SubjectCreationInput("História", grade4.getId()), admin);
		Subject subject23 = subjectService.createSubject(new SubjectCreationInput("Geografia", grade4.getId()), admin);
		Subject subject24 = subjectService.createSubject(new SubjectCreationInput("Biologia", grade4.getId()), admin);
		Subject subject25 = subjectService.createSubject(new SubjectCreationInput("Literatura", grade4.getId()), admin);
		Subject subject26 = subjectService.createSubject(new SubjectCreationInput("Filosofia", grade4.getId()), admin);*/
		
		//prof1
		subjectService.addTeacher(subject4.getId(), prof1.getId(), admin);
		subjectService.addTeacher(subject5.getId(), prof1.getId(), admin);
		subjectService.addTeacher(subject6.getId(), prof1.getId(), admin);
		
		//prof2
		subjectService.addTeacher(subject3.getId(), prof2.getId(), admin);
		subjectService.addTeacher(subject7.getId(), prof2.getId(), admin);
		/*subjectService.addTeacher(subject16.getId(), prof2.getId(), admin);
		subjectService.addTeacher(subject17.getId(), prof2.getId(), admin);
		subjectService.addTeacher(subject20.getId(), prof2.getId(), admin);
		subjectService.addTeacher(subject21.getId(), prof2.getId(), admin);*/
		
		//prof3
		subjectService.addTeacher(subject3.getId(), prof3.getId(), admin);
		subjectService.addTeacher(subject7.getId(), prof3.getId(), admin);
		subjectService.addTeacher(subject8.getId(), prof3.getId(), admin);
		subjectService.addTeacher(subject9.getId(), prof3.getId(), admin);
		subjectService.addTeacher(subject10.getId(), prof3.getId(), admin);
		subjectService.addTeacher(subject11.getId(), prof3.getId(), admin);
		
		//prof4
		/*subjectService.addTeacher(subject12.getId(), prof4.getId(), admin);
		subjectService.addTeacher(subject13.getId(), prof4.getId(), admin);
		subjectService.addTeacher(subject14.getId(), prof4.getId(), admin);
		subjectService.addTeacher(subject15.getId(), prof4.getId(), admin);
		subjectService.addTeacher(subject16.getId(), prof4.getId(), admin);
		subjectService.addTeacher(subject17.getId(), prof4.getId(), admin);
		subjectService.addTeacher(subject18.getId(), prof4.getId(), admin);
		
		//prof5
		subjectService.addTeacher(subject19.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject20.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject21.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject22.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject23.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject24.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject25.getId(), prof5.getId(), admin);
		subjectService.addTeacher(subject26.getId(), prof5.getId(), admin);*/
		
		//students - subject
		subjectService.addStudentToSubjectsInGrade(grade1.getId(), stud3.getId(), admin);
		subjectService.addStudentToSubjectsInGrade(grade1.getId(), stud4.getId(), admin);
		subjectService.addStudentToSubjectsInGrade(grade2.getId(), stud5.getId(), admin);
		subjectService.addStudentToSubjectsInGrade(grade2.getId(), stud6.getId(), admin);
		/*subjectService.addStudentToSubjectsInGrade(grade3.getId(), stud7.getId(), admin);
		subjectService.addStudentToSubjectsInGrade(grade3.getId(), stud8.getId(), admin);
		subjectService.addStudentToSubjectsInGrade(grade4.getId(), stud9.getId(), admin);
		subjectService.addStudentToSubjectsInGrade(grade4.getId(), stud10.getId(), admin);*/
		
		/*subjectService.addStudent(subject19.getId(), stud1.getId(), admin);
		subjectService.addStudent(subject12.getId(), stud1.getId(), admin);*/
		subjectService.addStudent(subject7.getId(), stud1.getId(), admin);
		subjectService.addStudent(subject4.getId(), stud1.getId(), admin);
		
		/*subjectService.addStudent(subject26.getId(), stud2.getId(), admin);
		subjectService.addStudent(subject18.getId(), stud2.getId(), admin);*/
		subjectService.addStudent(subject11.getId(), stud2.getId(), admin);
		subjectService.addStudent(subject6.getId(), stud2.getId(), admin);
		
		//discussion
		
		/*DiscussionCreationInput discussionInput = new DiscussionCreationInput("Dúvida da atividade 1", subject.getId(), "Pessoal, estou com dúvida na questão 6 da atividade 1. Alguem pode me explicar como faz essa divisão?");
		Discussion discussion = discussionService.createDiscussion(discussionInput, stud1);
		ReplyCreationInput replyInput1 = new ReplyCreationInput("Tem um exemplo muito parecido na página 56!", discussion.getId(), null);
		Reply reply = replyService.createReply(replyInput1, prof1);
		ReplyCreationInput replyInput2 = new ReplyCreationInput("Obrigado!", discussion.getId(), reply.getId());
		replyService.createReply(replyInput2, stud1);*/
		
		
	}

}