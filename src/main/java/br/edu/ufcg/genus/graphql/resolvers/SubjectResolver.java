package br.edu.ufcg.genus.graphql.resolvers;

import javax.xml.bind.DatatypeConverter;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;

public class SubjectResolver implements GraphQLResolver<Subject> {

    public Grade getGrade(Subject subject) {
        return subject.getGrade();
    }

    public Iterable<User> getTeachers(Subject subject) {
        return subject.getTeachers();
    }

    public Iterable<User> getStudents(Subject subject) {
        return subject.findStudents();
    }
    
    public Iterable<StudentSubject> getStudentSubjects(Subject subject) {
    	return subject.getStudents();
    }
    
    public String getPhoto(Subject subject) {
    	String result = null;
    	if (subject.getPhoto() != null) result = DatatypeConverter.printBase64Binary(subject.getPhoto());
        return result;
    }
}