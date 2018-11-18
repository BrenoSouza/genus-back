package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;

public class UserResolver implements GraphQLResolver<User> {

    public Iterable<Subject> getStudentSubjects(User user) {
        return user.findSubjectsStudent();
    }

    public Iterable<Subject> getTeacherSubjects(User user) {
        return user.getSubjects();
    }
}