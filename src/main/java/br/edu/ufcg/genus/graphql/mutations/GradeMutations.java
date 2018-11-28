package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.GradeCreationInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.services.GradeService;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.update_inputs.UpdateGradeInput;

public class GradeMutations implements GraphQLMutationResolver {
	
	@Autowired
	private GradeService gradeService;
	@Autowired
    private Validator validator;
	@Autowired
	private UserService userService;

	public Grade createGrade(GradeCreationInput input) {
		Set<ConstraintViolation<GradeCreationInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<GradeCreationInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalid attributes passed to creation of a grade.", errors);
		}
		return gradeService.createGrade(input, userService.findLoggedUser());
	}

	public Grade updateGrade(UpdateGradeInput input) {

        Set<ConstraintViolation<UpdateGradeInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<UpdateGradeInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalidattributes passed", errors);
        }

        return gradeService.updateGrade(input, userService.findLoggedUser());
    }
	
	public boolean removeGrade(long gradeId) {
		return this.gradeService.removeGrade(gradeId, userService.findLoggedUser());
	}
}
