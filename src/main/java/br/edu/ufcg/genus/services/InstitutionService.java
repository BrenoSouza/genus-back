package br.edu.ufcg.genus.services;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.genus.beans.AuthenticationBean;
import br.edu.ufcg.genus.beans.InstitutionBean;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.InstitutionRepository;
import br.edu.ufcg.genus.security.JwtTokenProvider;

@Service
public class InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
    private UserService userService;
	
    public Optional<Institution> findInstitution(Long id) {
        return institutionRepository.findById(id);
    }

    public Institution createInstitution(InstitutionBean input) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        //Optional<User> user = userService.findUserByEmail(email);

        Institution institution = new Institution();
        institution.setName(input.getName());
        institution.setEmail(input.getEmail());

        return institutionRepository.save(institution);
    }
}
