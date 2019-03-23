package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserInstitution;
import br.edu.ufcg.genus.models.UserRole;

@Component
public class EmailService {
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Autowired
	private InstitutionService institutionService;
	
	private boolean sendSimpleMessage(String[] to, String subject, String text, String textEnding) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text + textEnding);
        emailSender.send(message);
        return true;
	}
	
	public Boolean sendEmailToTeachers(Long institutionId, User sender, String subject, String text) {
		Institution institution = this.institutionService.findById(institutionId);
		List<String> teacherEmails = new ArrayList<>();
		for(UserInstitution instUser : institution.getUsers()) {
			if (instUser.getRole().equals(UserRole.TEACHER)) {
				teacherEmails.add(instUser.getUser().getEmail());
			}
		}
		String textEnding = "\n\n Mensagem enviada por " + sender.getUsername() + " para todos os professores da instituicao " + institution.getName();
		return sendSimpleMessage(toArray(teacherEmails), subject, text, textEnding);
	}
	
	public Boolean sendEmailToStudents(Long institutionId, User sender, String subject, String text) {
		Institution institution = this.institutionService.findById(institutionId);
		List<String> studentEmails = new ArrayList<>();
		for(UserInstitution instUser : institution.getUsers()) {
			if (instUser.getRole().equals(UserRole.STUDENT)) {
				studentEmails.add(instUser.getUser().getEmail());
			}
		}
		String textEnding = "\n\n Mensagem enviada por " + sender.getUsername() + " para todos os estudantes da instituicao " + institution.getName();
		return sendSimpleMessage(toArray(studentEmails), subject, text, textEnding);
		
	}
	
	public Boolean sendEmailToGradeStudents(Grade grade, User sender, String subject, String text) {
		List<String> studentEmails = new ArrayList<>();
		for(User user: grade.getStudents().keySet()) {
			studentEmails.add(user.getEmail());
		}
		String textEnding = "\n\n Mensagem enviada por " + sender.getUsername() + " para todos os estudantes na serie " + grade.getName() + " da instituicao " + grade.getInstitution().getName();
		return sendSimpleMessage(toArray(studentEmails), subject, text, textEnding);
	}
	
	public Boolean sendEmailToSubjectStudents(Subject subjectObj, User sender, String subject, String text) {
		List<String> studentEmails = new ArrayList<>();
		for(StudentSubject studSub : subjectObj.getStudents()) {
			studentEmails.add(studSub.getUser().getEmail());
		}
		String textEnding = "\n\n Mensagem enviada por " + sender.getUsername() + " para todos os estudantes na disciplina " + subjectObj.getName() + " da instituicao " + subjectObj.getGrade().getInstitution().getName();
		return sendSimpleMessage(toArray(studentEmails), subject, text, textEnding);
	}
	
	private String[] toArray(List<String> list) {
		String[] result = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

}
