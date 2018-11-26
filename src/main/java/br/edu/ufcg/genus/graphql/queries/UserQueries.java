package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.AuthenticationInput;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.InstitutionRepository;
import br.edu.ufcg.genus.services.UserService;

public class UserQueries implements GraphQLQueryResolver {
	
	@Autowired
    private UserService userService;
    @Autowired
    private Validator validator;
	@Autowired
	private InstitutionRepository institutionRepository;

	public String login (AuthenticationInput input) {
        Set<ConstraintViolation<AuthenticationInput>> violations = validator.validate(input);

		if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
        	for (ConstraintViolation<AuthenticationInput> v : violations) {
                extensions.put(v.getMessage(), v.getInvalidValue());
        	}
            throw new InvalidAttributesException("Invalid attributes passed to login.", extensions);
        }
		return userService.login(input);		
	}

    public User findUser(long userId) {
        return userService.findUserById(userId);
    }

    public User findLoggedUser() {
        return userService.findLoggedUser();
    }
    
    public UserRole findRole(Long institutionId) {
    	return userService.findRole(institutionId, userService.findLoggedUser());
    }

    public Iterable<Subject> findSubjectsByUser(Long userId, Long institutionId) {
        User user = userService.findUserById(userId);

        Institution institution = this.institutionRepository.findById(institutionId).get();
		List<Subject> result = new ArrayList<>();
		for (Subject subject : user.getSubjects()) {
			if (subject.getGrade().getInstitution().equals(institution)) {
				result.add(subject);
			}
		}
		return result;
    }
    
    public Iterable<User> findTeachersBySubject(Long subjectId) {
		return this.userService.findTeachersBySubject(subjectId);
	}
    
    public Iterable<User> findStudentsBySubject(Long subjectId) {
    	return this.userService.findStudentsBySubject(subjectId);
    }
}
