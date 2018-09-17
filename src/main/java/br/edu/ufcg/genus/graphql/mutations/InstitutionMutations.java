package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.edu.ufcg.genus.beans.InstitutionBean;
import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.InstitutionService;

public class InstitutionMutations implements GraphQLMutationResolver {
	
	@Autowired
	private InstitutionService institutionService;
	@Autowired
    private Validator validator;

	public Institution createInstitution(InstitutionBean input) {

        Set<ConstraintViolation<InstitutionBean>> violations = validator.validate(input);
        if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
            violations.forEach((ConstraintViolation<InstitutionBean> v) -> {
                extensions.put(v.getMessage(), v.getInvalidValue());
            });
            throw new InvalidAttributesException("Invalid attributes passed to creation of an institution", extensions);
		}
		return institutionService.createInstitution(input);
	}
}
