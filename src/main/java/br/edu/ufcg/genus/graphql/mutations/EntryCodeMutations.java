package br.edu.ufcg.genus.graphql.mutations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.CreateAdvancedEntryCodeInput;
import br.edu.ufcg.genus.inputs.CreateEntryCodeInput;
import br.edu.ufcg.genus.inputs.CreateInstitutionInput;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.EntryCodeService;
import br.edu.ufcg.genus.services.UserService;

public class EntryCodeMutations implements GraphQLMutationResolver{
	
	@Autowired
	private EntryCodeService entryCodeService;
	@Autowired
	private UserService userService;
	@Autowired
    private Validator validator;
	
	public String createEntryCode(CreateEntryCodeInput input) {
		return this.entryCodeService.createEntryCode(input, userService.findLoggedUser());
	}
	
	public String createAdvancedEntryCode(CreateAdvancedEntryCodeInput input) {
		Set<ConstraintViolation<CreateAdvancedEntryCodeInput>> violations = validator.validate(input);
		if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<CreateAdvancedEntryCodeInput> v) -> {
                errors.add(v.getMessage());
			});
            throw new InvalidAttributesException("Invalid attributes passed to creation of an advanced entry code.", errors);
		}
		return this.entryCodeService.createAdvancedEntryCode(input, userService.findLoggedUser());
	}
	
	public Institution joinInstitution(String code) {
		return this.entryCodeService.joinInstitution(code, userService.findLoggedUser());
	}
}
