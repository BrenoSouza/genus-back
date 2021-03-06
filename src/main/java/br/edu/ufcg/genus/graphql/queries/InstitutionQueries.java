package br.edu.ufcg.genus.graphql.queries;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.inputs.GetUsersFromInstitutionByRoleInput;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.services.InstitutionService;
import br.edu.ufcg.genus.services.UserService;

public class InstitutionQueries implements GraphQLQueryResolver {
	
	@Autowired
	private InstitutionService institutionService;
	@Autowired
	private UserService userService;

    public Institution findInstitution(Long institutionId) {
        return institutionService.findById(institutionId);
    }
    
    public List<Institution> getInstitutionsFromLoggedUser() {
		return this.institutionService.getInstitutionsFromUser(userService.findLoggedUser());
    }
    
    public List<User> getAllUsersFromInstitution(Long institutionId) {
    	return this.institutionService.getAllUsersFromInstitution(institutionId);
    }
    
    public List<User> getUsersFromInstitutionByRole(GetUsersFromInstitutionByRoleInput input) {
    	return this.institutionService.getUsersFromInstitutionByRole(input);
    }
}
