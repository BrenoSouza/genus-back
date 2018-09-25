package br.edu.ufcg.genus.graphql.queries;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.InstitutionService;

public class InstitutionQueries implements GraphQLQueryResolver {
	
	@Autowired
	InstitutionService institutionService;

    public Institution findInstitution(Long institutionId) {
        return institutionService.findById(institutionId).orElse(null);
    }
    
    public List<Institution> getInstitutionsFromLoggedUser() {
		return this.institutionService.getInstitutionsFromLoggedUser();
	}
}
