package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.AuthenticationInput;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.services.UserService;

public class UserQueries implements GraphQLQueryResolver {
	
	@Autowired
	private UserService userService;
	
	public String login (AuthenticationInput input) {
		return userService.login(input);		
	}

    public User findUser(long id) {
        return userService.findUserById(id).orElseThrow(() -> new InvalidIDException());
    }

    public User findLoggedUser() {
        return userService.findLoggedUser();
    }
    
    public UserRole findRole(Long institutionId) {
    	return userService.findRole(institutionId);
    }

}
