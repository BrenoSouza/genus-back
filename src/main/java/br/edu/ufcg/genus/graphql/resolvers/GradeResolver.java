package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;

public class GradeResolver implements GraphQLResolver<Grade> {

    public Iterable<Subject> getSubjects(Grade grade) {
        return grade.getSubjects();
    }

    public Institution getInstitution(Grade grade) {
        return grade.getInstitution();
    }
    
    public Iterable<User> getStudents(Grade grade) {
    	return grade.getStudents().keySet();
    }
    
    public Iterable<User> getTeachers(Grade grade) {
    	return grade.getTeachers().keySet();
    }
    
    public Integer getQntTeachers(Grade grade) {
    	return grade.getTeachers().size();
    }
    
    public Integer getQntStudents(Grade grade) {
    	return grade.getStudents().size();
    }
}