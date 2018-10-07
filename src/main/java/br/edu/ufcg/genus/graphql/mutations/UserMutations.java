package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.services.UserService;

public class UserMutations implements GraphQLMutationResolver {
	
	@Autowired
	private UserService userService;
	@Autowired
    private Validator validator;

	public User createUser(CreateUserInput input) {

		Set<ConstraintViolation<CreateUserInput>> violations = validator.validate(input);

		if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
            violations.forEach((ConstraintViolation<CreateUserInput> v) -> {
                extensions.put(v.getMessage(), v.getMessage());
            });
			throw new InvalidAttributesException("Invalid attributes passed to creation of an user.", extensions);
		}
		
		return userService.createUser(input);
	}

}
