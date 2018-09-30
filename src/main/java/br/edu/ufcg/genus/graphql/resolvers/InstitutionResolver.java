package br.edu.ufcg.genus.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.GradeService;

public class InstitutionResolver implements GraphQLResolver<Institution> {

    @Autowired
    private GradeService gradeService;

    public Iterable<Grade> getGrades(Institution institution) {
        return gradeService.findGradesByInstitution(institution.getId());
    }
}