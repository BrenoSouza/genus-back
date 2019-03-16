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
	private InstitutionService institutionService;
	
	@Autowired
	private SubjectService subjectService;
	
	public Grade createGrade(GradeCreationInput input, User user) {
		Institution institution = this.institutionService.findById(input.getInstitutionId());
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
	
	public void saveGradeInRepository(Grade grade) {
		this.gradeRepository.save(grade);
	}
	
	public void addSubjectToGrade(Grade grade, Subject newSubject) {
		grade.addSubject(newSubject);
		this.gradeRepository.save(grade);
	}
	
    public Iterable<Grade> findGradesByInstitution(Long institutionId) {
		Institution institution = this.institutionService.findById(institutionId);
        return institution.getGrades();
	}

	public Grade updateGrade(UpdateGradeInput input, User user) {
		Grade grade = findGradeById(input.getGradeId());
		checkAdminPermission(grade, user);
		        if (input.getName() != null) {
            grade.setName(input.getName());
		}
        return gradeRepository.save(grade);
    }
	
	public boolean removeGrade(long gradeId, User user) {
		Grade grade = findGradeById(gradeId);
		checkAdminPermission(grade, user);
		for(Subject subject: grade.getSubjects()) { //this should be optimized
			subjectService.removeSubject(subject.getId(), user);
		}
		gradeRepository.deleteById(gradeId);				
		return true;
	}
	
	private void checkAdminPermission(Grade grade, User user) {
		List<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		long institutionId = grade.getInstitution().getId();
		PermissionChecker.checkPermission(user, institutionId, permitedRoles);
	}

}
