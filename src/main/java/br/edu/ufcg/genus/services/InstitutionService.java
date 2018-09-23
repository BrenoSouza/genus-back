package br.edu.ufcg.genus.services;

import br.edu.ufcg.genus.exception.InvalidTokenException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.genus.beans.CreateInstitutionInput;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.InstitutionRepository;

@Service
public class InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
    private UserService userService;
	
    public Optional<Institution> findById(Long id) {
        return institutionRepository.findById(id);
    }

    public Institution createInstitution(CreateInstitutionInput input) {
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userService.findUserByEmail(email)
        .orElseThrow(() -> new InvalidTokenException("Token is not valid"));

        Institution institution = new Institution();
        institution.setName(input.getName());
        institution.setEmail(input.getEmail());
        institution.setPhone(input.getPhone());
        institution.setAddress(input.getAddress());
        //institution.setOwner(owner);
        institutionRepository.save(institution);
        userService.addRole(owner, institution, UserRole.ADMIN);
        return institution;
    }

	public void addGradeToInstitution(Institution institution, Grade newGrade) {
		institution.addGrade(newGrade);
		institutionRepository.save(institution);
	}
}