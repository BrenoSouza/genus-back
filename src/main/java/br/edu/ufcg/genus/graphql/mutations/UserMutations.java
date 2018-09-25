package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.services.UserService;

public class UserMutations implements GraphQLMutationResolver {
	
	@Autowired
	UserService userService;
	
	public User createUser(CreateUserInput input) {
		return userService.createUser(input);
	}

}
