package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.services.SubjectService;
import br.edu.ufcg.genus.services.UserService;

public class SubjectMutations implements GraphQLMutationResolver {
	
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private UserService userService;
	@Autowired
    private Validator validator;

	public Subject createSubject(SubjectCreationInput input) {
		Set<ConstraintViolation<SubjectCreationInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
            violations.forEach((ConstraintViolation<SubjectCreationInput> v) -> {
                extensions.put(v.getMessage(), v.getMessage());
            });
            throw new InvalidAttributesException("Invalid attributes passed to creation of a subject.", extensions);
		}

		return this.subjectService.createSubject(input);		
	}

	public void addTeacher(Long subjectId, Long teacherId) {
		this.userService.addSubject(subjectId, teacherId);
	}

}
