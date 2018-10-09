package br.edu.ufcg.genus.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.AuthenticationInput;
import br.edu.ufcg.genus.inputs.CreateUserInput;
import br.edu.ufcg.genus.exception.InvalidCredentialsException;
import br.edu.ufcg.genus.exception.InvalidTokenException;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.UserRepository;
import br.edu.ufcg.genus.security.JwtTokenProvider;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
    @Autowired
	private SubjectService subjectService;
	@Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
	
	public User createUser (CreateUserInput input) {
		User newUser = new User(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
		return this.userRepository.save(newUser);
	}

	public String login (AuthenticationInput input) {
        String email = input.getEmail();
        String password = input.getPassword();
                
		try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
            		.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password.", null));
            
            return jwtTokenProvider.createToken(email, user.getRoles());
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid email or password", null);
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

    public Subject addSubject(Long subjectId, Long teacherId) {
        Optional<User> teacher = this.userRepository.findById(teacherId);
        Optional<Subject> subject = this.subjectService.findSubjectById(subjectId);

        teacher.get().addSubject(subject.get());

        this.saveUserInRepository(teacher.get());

        return this.subjectService.findSubjectById(subjectId).get();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
	}
    
    public void saveUserInRepository(User user) {
    	userRepository.save(user);
    }
    
    public boolean passwordMatch(User user, String password) {
    	return passwordEncoder.matches(password, user.getPassword());
    }
	
	public UserRole findRole(Long institutionId) {
		User user = findLoggedUser();
		return user.getRole(institutionId);
	}
}
