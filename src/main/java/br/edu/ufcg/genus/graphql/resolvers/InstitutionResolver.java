package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;

public class InstitutionResolver implements GraphQLResolver<Institution> {

    public Iterable<Grade> getGrades(Institution institution) {
        return institution.getGrades();
    }
}