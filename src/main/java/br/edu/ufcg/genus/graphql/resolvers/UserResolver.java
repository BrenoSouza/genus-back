package br.edu.ufcg.genus.graphql.resolvers;

import javax.xml.bind.DatatypeConverter;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;

public class UserResolver implements GraphQLResolver<User> {

    public Iterable<Subject> getStudentSubjects(User user) {
        return user.findSubjectsStudent();
    }
    
    public Iterable<StudentSubject> getStudentSubjectRelations(User user) {
    	return user.getSubjectsStudent();
    }

    public Iterable<Subject> getTeacherSubjects(User user) {
        return user.getSubjects();
    }

    public Iterable<Notification> getNotifications(User user) {
        return user.getNotifications();
    }
    
    public String getPhoto(User user) {
    	String result = null;
    	if (user.getPhoto() != null) result = DatatypeConverter.printBase64Binary(user.getPhoto());
        return result;
    }
}