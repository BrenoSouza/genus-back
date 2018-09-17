package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.InstitutionService;

public class InstitutionQueries implements GraphQLQueryResolver {
	
	@Autowired
	InstitutionService institutionService;

    public Optional<Institution> findInstitution(Long institutionId) {
        return institutionService.findInstitution(institutionId);
    }
}
