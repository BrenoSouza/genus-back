package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.update_inputs.UpdateUserInput;
import br.edu.ufcg.genus.utils.ServerConstants;

public class UserMutations implements GraphQLMutationResolver {
	
	@Autowired
	private UserService userService;
	@Autowired
    private Validator validator;

	public User createUser(CreateUserInput input) {

		Set<ConstraintViolation<CreateUserInput>> violations = validator.validate(input);

		if (violations.size() > 0) {
			List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<CreateUserInput> v) -> {
                errors.add(v.getMessage());
            });
			throw new InvalidAttributesException("Invalid attributes passed to creation of an user.", errors);
		}
		
		return userService.createUser(input);
	}

	public User updateUser(UpdateUserInput input) {

        Set<ConstraintViolation<UpdateUserInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<UpdateUserInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalidattributes passed", errors);
        }

        return userService.updateUser(input);
    }

    public boolean updateUserPassword(String password, String newPassword) {

        if (newPassword.length() < ServerConstants.MIN_LOGIN_FIELD) {

        	List<String> errors = new ArrayList<>();
            errors.add("PASSWORD_INVALID_MIN_LENGTH");

            throw new InvalidAttributesException("Passed password is invalid", errors);
        }

        return userService.updateUserPassword(password, newPassword);
    }
}
