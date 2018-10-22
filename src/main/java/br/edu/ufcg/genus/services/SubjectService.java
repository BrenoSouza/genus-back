package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.InvalidPermissionException;
import br.edu.ufcg.genus.exception.InvalidTokenException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.update_inputs.UpdateSubjectInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private SubjectService subjectService;

	@Autowired
    private UserService userService;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private GradeService gradeService;
	
	public Subject createSubject(SubjectCreationInput input) {
		Grade grade = this.gradeService.findGradeById(input.getGradeId());
		Institution institution = this.institutionService.findById(grade.getInstitution().getId())
				.orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", grade.getInstitution().getId()));
		User user = this.userService.findLoggedUser();
		ArrayList<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		PermissionChecker.checkPermission(user, institution.getId(), permitedRoles);
		Subject newSubject = new Subject(grade, input.getName());
		subjectRepository.save(newSubject);
		return newSubject;
	}
	
	public Optional<Subject> findSubjectById(long id) {
		return this.subjectRepository.findById(id);
	}

	public Iterable<Subject> findSubjectsByGrade(Long gradeId) {
		Grade grade = this.gradeService.findGradeById(gradeId);

		return grade.getSubjects();
	}
		
	public Iterable<User> findTeachersBySubject(Long subjectId) {
		Subject subject = this.subjectService.findSubjectById(subjectId)
			.orElseThrow(() -> new InvalidIDException("Subject with passed ID was not found", subjectId));

        return subject.getTeachers();
    }

	public Subject updateSubject(UpdateSubjectInput input) {
		List<UserRole> permittedRoles = new ArrayList<>();
		permittedRoles.add(UserRole.ADMIN);

        User user = this.userService.findLoggedUser();

		Subject subject = findSubjectById(input.getSubjectId())
			.orElseThrow(() -> new InvalidTokenException("Token is not valid"));

		Grade grade = subject.getGrade();

		Institution institution = grade.getInstitution();
		
		if(!user.getRole(institution.getId()).equals(UserRole.ADMIN)) throw new InvalidPermissionException(permittedRoles);

        if (input.getName() != null) {
            subject.setName(input.getName());
		}
		
        return subjectRepository.save(subject);
	}

}
