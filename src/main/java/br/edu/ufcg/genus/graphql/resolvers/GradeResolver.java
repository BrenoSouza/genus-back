package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;

public class GradeResolver implements GraphQLResolver<Grade> {

    public Iterable<Subject> getSubjects(Grade grade) {
        return grade.getSubjects();
    }

    public Institution getInstitution(Grade grade) {
        return grade.getInstitution();
    }
}