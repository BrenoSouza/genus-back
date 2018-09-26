package br.edu.ufcg.genus.services;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.InvalidTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.genus.inputs.CreateInstitutionInput;
import br.edu.ufcg.genus.models.EntryCode;
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
        .orElseThrow(() -> new InvalidTokenException("Token inválido."));

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
	
	public List<Institution> getInstitutionsFromLoggedUser() {
		User user = this.userService.findLoggedUser();
		Set<Long> institutionsIds = user.getInstitutionsIDs();
		List<Institution> institutions = new ArrayList<>();
		for (Long id : institutionsIds) {
			Institution institution = findById(id)
					.orElseThrow(() -> new InvalidIDException());
			institutions.add(institution);
		}
		return institutions;
	}
}