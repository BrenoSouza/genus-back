package br.edu.ufcg.genus.graphql.queries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.inputs.SendEmailInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.services.EmailService;
import br.edu.ufcg.genus.services.GradeService;
import br.edu.ufcg.genus.services.SubjectService;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.utils.PermissionChecker;

public class EmailQueries implements GraphQLQueryResolver {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private SubjectService subjectService;
	
	
	public Boolean sendEmailToTeachers(SendEmailInput input, Long institutionId) {
		User user = userService.findLoggedUser();
		List<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		permitedRoles.add(UserRole.TEACHER);
		PermissionChecker.checkPermission(user, institutionId, permitedRoles);
		return emailService.sendEmailToTeachers(institutionId, user, input.getSubject(), input.getText());	
		
	}
	
	public Boolean sendEmailToStudents(SendEmailInput input, Long institutionId) {
		User user = userService.findLoggedUser();
		List<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		PermissionChecker.checkPermission(user, institutionId, permitedRoles);
		return emailService.sendEmailToStudents(institutionId, user, input.getSubject(), input.getText());	
		
	}
	
	public Boolean sendEmailToGradeStudents(SendEmailInput input, Long gradeId) {
		User user = userService.findLoggedUser();
		Grade grade = gradeService.findGradeById(gradeId);
		PermissionChecker.checkSendMailToGradePermission(user, grade);
		return emailService.sendEmailToGradeStudents(grade, user, input.getSubject(), input.getText());
		
	}
	
	public Boolean sendEmailToSubjectStudents(SendEmailInput input, Long subjectId) {
		User user = userService.findLoggedUser();
		Subject subject = subjectService.findSubjectById(subjectId);
		PermissionChecker.checkSendMailToSubjectPermission(user, subject);
		return emailService.sendEmailToSubjectStudents(subject, user, input.getSubject(), input.getText());
	}
	
}
