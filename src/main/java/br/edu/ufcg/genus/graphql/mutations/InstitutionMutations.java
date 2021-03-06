package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.edu.ufcg.genus.inputs.CreateInstitutionInput;
import br.edu.ufcg.genus.inputs.RemoveUserFromInstitutionInput;
import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.InstitutionService;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.update_inputs.UpdateInstitutionInput;

public class InstitutionMutations implements GraphQLMutationResolver {
	
	@Autowired
	private InstitutionService institutionService;
	@Autowired
    private Validator validator;
	@Autowired
	private UserService userService;

	public Institution createInstitution(CreateInstitutionInput input) {

        Set<ConstraintViolation<CreateInstitutionInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<CreateInstitutionInput> v) -> {
                errors.add(v.getMessage());
			});
            throw new InvalidAttributesException("Invalid attributes passed to creation of an institution.", errors);
		}
		return institutionService.createInstitution(input, userService.findLoggedUser());
	}
	
	public boolean removeUserFromInstitution (RemoveUserFromInstitutionInput input) {
		return this.institutionService.removeUserFromInstitution(input, userService.findLoggedUser());
	}

	public Institution updateInstitution(UpdateInstitutionInput input) {

        Set<ConstraintViolation<UpdateInstitutionInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<UpdateInstitutionInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalidattributes passed", errors);
        }

        return institutionService.updateInstitution(input, userService.findLoggedUser());
    }
}
