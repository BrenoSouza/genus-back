package br.edu.ufcg.genus.graphql.queries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.inputs.SendEmailInput;
import br.edu.ufcg.genus.models.EmailType;
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
	
	public Boolean sendEmail(SendEmailInput input, Long id, EmailType type) {
		User user = userService.findLoggedUser();
		if (type == EmailType.TO_ALL_STUDENTS) {
			List<UserRole> permitedRoles = new ArrayList<>();
			permitedRoles.add(UserRole.ADMIN);
			PermissionChecker.checkPermission(user, id, permitedRoles);
			return emailService.sendEmailToStudents(id, user, input.getSubject(), input.getText());
		} else if (type == EmailType.TO_ALL_TEACHERS) {
			List<UserRole> permitedRoles = new ArrayList<>();
			permitedRoles.add(UserRole.ADMIN);
			permitedRoles.add(UserRole.TEACHER);
			PermissionChecker.checkPermission(user, id, permitedRoles);
			return emailService.sendEmailToTeachers(id, user, input.getSubject(), input.getText());
		} else if (type == EmailType.TO_ALL_GRADE_STUDENTS) {
			Grade grade = gradeService.findGradeById(id);
			PermissionChecker.checkSendMailToGradePermission(user, grade);
			return emailService.sendEmailToGradeStudents(grade, user, input.getSubject(), input.getText());
		} else if (type == EmailType.TO_ALL_SUBJECT_STUDENTS) {
			Subject subject = subjectService.findSubjectById(id);
			PermissionChecker.checkSendMailToSubjectPermission(user, subject);
			return emailService.sendEmailToSubjectStudents(subject, user, input.getSubject(), input.getText());
		} else {
			return false;
		}
	}	
}
