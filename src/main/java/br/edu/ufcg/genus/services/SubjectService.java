package br.edu.ufcg.genus.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.beans.SubjectCreationInput;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private GradeService gradeService;
	
	
	
	public Subject createSubject(SubjectCreationInput input) {
		Grade grade = this.gradeService.findGradeById(input.getGradeId())
				.orElseThrow(() -> new InvalidIDException());
		Institution institution = this.institutionService.findById(grade.getInstitutionId())
				.orElseThrow(() -> new InvalidIDException());
		User user = this.userService.findLoggedUser();
		//if (!institution.getOwner().equals(user)) throw new RuntimeException("Only owners can do this action"); // CRIAR UMA EXCEPTION
		ArrayList<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		PermissionChecker.checkPermission(user, institution.getId(), permitedRoles);
		Subject newSubject = new Subject(grade, input.getName());
		subjectRepository.save(newSubject);
		this.gradeService.addSubjectToGrade(grade, newSubject);
		return newSubject;
	}

}
