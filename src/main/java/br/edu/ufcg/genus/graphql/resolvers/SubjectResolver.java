package br.edu.ufcg.genus.graphql.resolvers;

import java.util.Set;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Subject;

public class SubjectResolver implements GraphQLResolver<Subject> {

    public Grade getGrade(Subject subject) {
        return subject.getGrade();
    }
}