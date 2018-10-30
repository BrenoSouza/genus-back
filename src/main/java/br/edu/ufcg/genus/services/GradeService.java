package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.GradeCreationInput;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.GradeRepository;
import br.edu.ufcg.genus.update_inputs.UpdateGradeInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class GradeService {
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private SubjectService subjectService;
	
	public Grade createGrade(GradeCreationInput input) {
		User user = this.userService.findLoggedUser();
		Institution institution = this.institutionService.findById(input.getInstitutionId())
				.orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", input.getInstitutionId()));

		ArrayList<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		PermissionChecker.checkPermission(user, institution.getId(), permitedRoles);
		Grade newGrade = new Grade(input.getName(), institution);
		this.gradeRepository.save(newGrade);
		return newGrade;		
	}
	
	public Grade findGradeById(Long id) {
		return this.gradeRepository.findById(id)
				.orElseThrow(() -> new InvalidIDException("Grade with passed ID was not found", id));
	}
	
	public void addSubjectToGrade(Grade grade, Subject newSubject) {
		grade.addSubject(newSubject);
		this.gradeRepository.save(grade);
	}
	
    public Iterable<Grade> findGradesByInstitution(Long institutionId) {
		Institution institution = this.institutionService.findById(institutionId)
			.orElseThrow(() -> new InvalidIDException("Institution with passed ID was not found", institutionId));

        return institution.getGrades();
	}

	public Grade updateGrade(UpdateGradeInput input) {
		Grade grade = findGradeById(input.getGradeId());
		checkAdminPermission(grade);
		        if (input.getName() != null) {
            grade.setName(input.getName());
		}
        return gradeRepository.save(grade);
    }
	
	public boolean removeGrade(long gradeId) {
		Grade grade = findGradeById(gradeId);
		checkAdminPermission(grade);
		for(Subject subject: grade.getSubjects()) { //this should be optimized
			subjectService.removeSubject(subject.getId());
		}
		gradeRepository.deleteById(gradeId);				
		return true;
	}
	
	private void checkAdminPermission(Grade grade) {
		List<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		User user = this.userService.findLoggedUser();
		long institutionId = grade.getInstitution().getId();
		PermissionChecker.checkPermission(user, institutionId, permitedRoles);
	}

}
