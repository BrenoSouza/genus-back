package br.edu.ufcg.genus.services;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.AuthenticationInput;
import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.exception.InvalidTokenException;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.UserRepository;
import br.edu.ufcg.genus.security.JwtTokenProvider;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private Validator validator;
	
	public User createUser (CreateUserInput input) {
		Set<ConstraintViolation<CreateUserInput>> violations = validator.validate(input);

		if (violations.size() > 0) {
        	String errorString = "";
        	for (ConstraintViolation<CreateUserInput> v : violations) {
        		errorString = " " + errorString + v.getMessage();
        	}
            throw new RuntimeException("Atributos passados na criação do usuário são inválidos." + errorString);
        }
		User newUser = new User(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
		return this.userRepository.save(newUser);
	}

	public String login (AuthenticationInput input) {
        String email = input.getEmail();
        String password = input.getPassword();

		try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
            		.orElseThrow(() -> new RuntimeException("Email ou senha inválido.", null));
            
            return jwtTokenProvider.createToken(email, user.getRoles());
        } catch (Exception e) {
            throw new RuntimeException("Email ou senha inválido.", e);
		}		
    }
    
    public User findLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = findUserByEmail(email)
            .orElseThrow(() -> new InvalidTokenException("Token is not valid"));

        return user;
    }

	public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
	}

	public void addRole(User user, Institution institution, UserRole role) {
		user.addRole(institution.getId(), role);	
		userRepository.save(user);
	}
	
	public UserRole findRole(Long institutionId) {
		User user = findLoggedUser();
		return user.getRole(institutionId);
	}
}
