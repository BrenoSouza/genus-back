package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.GradeCreationInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.services.GradeService;

public class GradeMutations implements GraphQLMutationResolver {
	
	@Autowired
	private GradeService gradeService;
	@Autowired
    private Validator validator;

	public Grade createGrade(GradeCreationInput input) {
		Set<ConstraintViolation<GradeCreationInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
            violations.forEach((ConstraintViolation<GradeCreationInput> v) -> {
                extensions.put(v.getMessage(), v.getMessage());
            });
            throw new InvalidAttributesException("Invalid attributes passed to creation of a grade.", extensions);
		}
		return gradeService.createGrade(input);
	}
}
