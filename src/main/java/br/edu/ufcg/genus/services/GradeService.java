package br.edu.ufcg.genus.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.beans.GradeCreationInput;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.GradeRepository;

@Service
public class GradeService {
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private InstitutionService institutionService;
	
	public Grade createGrade(GradeCreationInput input) {
		User user = this.userService.findLoggedUser();
		Institution institution = this.institutionService.findById(input.getInstitutionId())
				.orElseThrow(() -> new InvalidIDException());
		if (!institution.getOwner().equals(user)) throw new RuntimeException("Only owners can do this action"); // CRIAR UMA EXCEPTION
		Grade newGrade = new Grade(input.getName(), input.getInstitutionId());
		this.gradeRepository.save(newGrade);
		institutionService.addGradeToInstitution(institution, newGrade);
		return newGrade;		
	}
	
	public Optional<Grade> findGradeById(Long id) {
		return this.gradeRepository.findById(id);
	}
	
	public void addSubjectToGrade(Grade grade, Subject newSubject) {
		grade.addSubject(newSubject);
		this.gradeRepository.save(grade);
	}

}
