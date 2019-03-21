package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.services.SubjectService;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.update_inputs.UpdateSubjectInput;

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
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<SubjectCreationInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalid attributes passed to creation of a subject.", errors);
		}

		return this.subjectService.createSubject(input, userService.findLoggedUser());		
	}

	public Subject addTeacherToSubject(Long subjectId, Long teacherId) {
		return this.subjectService.addTeacher(subjectId, teacherId, userService.findLoggedUser());
    }
    
    public Subject addStudentToSubject(Long subjectId, Long studentId) {
		return this.subjectService.addStudent(subjectId, studentId, userService.findLoggedUser());
	}

	public Subject updateSubject(UpdateSubjectInput input) {

        Set<ConstraintViolation<UpdateSubjectInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<UpdateSubjectInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalid attributes passed", errors);
        }

        return subjectService.updateSubject(input, userService.findLoggedUser());
    }
    
	public boolean removeSubject(long subjectId) {
		return this.subjectService.removeSubject(subjectId, userService.findLoggedUser());
	}
	
	public List<Subject> addStudentToSubjectsInGrade(Long gradeId, Long studentId) {
		return this.subjectService.addStudentToSubjectsInGrade(gradeId, studentId, userService.findLoggedUser());
	}
	
	public boolean removeInstitutionSubjectsFromUser(Long institutionId, Long studentId) {
		return this.subjectService.removeInstitutionSubjectsFromUser(institutionId, studentId, userService.findLoggedUser());
	}
	
	public boolean removeStudentFromSubject(Long subjectId, Long studentId) {
		return this.subjectService.removeStudentFromSubject(subjectId, studentId, userService.findLoggedUser());
	}
	
	public boolean removeTeacherFromSubject(Long subjectId, Long teacherId) {
		return this.subjectService.removeTeacherFromSubject(subjectId, teacherId, userService.findLoggedUser());
	}
	
	public boolean removeEveryStudentFromSubject(Long subjectId) {
		return this.subjectService.removeEveryStudentFromSubject(subjectId, userService.findLoggedUser());
	}
	
	public Subject moveStudentsFromSubject(Long fromId, Long toId) {
		return this.subjectService.moveStudentsFromSubject(fromId, toId, userService.findLoggedUser());
	}

}
