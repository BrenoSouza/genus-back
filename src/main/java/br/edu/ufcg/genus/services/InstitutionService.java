package br.edu.ufcg.genus.services;

import br.edu.ufcg.genus.exception.InvalidTokenException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.genus.beans.InstitutionBean;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
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

    public Iterable<Institution> findInstitutionsByOwner(Long ownerId) {
        return institutionRepository.findByOwnerId(ownerId);
    }

    public Institution createInstitution(InstitutionBean input) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userService.findUserByEmail(email)
        .orElseThrow(() -> new InvalidTokenException("Token is not valid"));

        Institution institution = new Institution();
        institution.setName(input.getName());
        institution.setEmail(input.getEmail());
        institution.setPhone(input.getPhone());
        institution.setAddress(input.getAddress());
        institution.setOwner(owner);

        return institutionRepository.save(institution);
    }

	public void addGradeToInstitution(Institution institution, Grade newGrade) {
		institution.addGrade(newGrade);
		institutionRepository.save(institution);
	}
}