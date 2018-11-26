package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.List;
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
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.InvalidPermissionException;
import br.edu.ufcg.genus.exception.InvalidTokenException;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.StudentSubjectRepository;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.repositories.UserRepository;
import br.edu.ufcg.genus.security.JwtTokenProvider;
import br.edu.ufcg.genus.update_inputs.UpdateUserInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

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

	public User findUserById(long id) {
        return userRepository.findById(id)
        		.orElseThrow(() -> new InvalidIDException("User with passed ID was not found", id));
    }
	
	public Iterable<User> findTeachersBySubject(Long subjectId) {
		Subject subject = this.subjectService.findSubjectById(subjectId);
        return subject.getTeachers();
	}
	
	public Iterable<User> findStudentsBySubject(Long subjectId) {
		Subject subject = this.subjectService.findSubjectById(subjectId);
        return subject.findStudents();
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
	
	public UserRole findRole(Long institutionId, User user) {
		return user.getRole(institutionId);
    }
    
    public User updateUser(UpdateUserInput input) {

        User user = findLoggedUser();

        if (input.getUsername() != null) {
            user.setUsername(input.getUsername());
        }

        return userRepository.save(user);
    }

    public boolean updateUserPassword(String password, String newPassword) {
		List<UserRole> permittedRoles = new ArrayList<>();
        User user = findLoggedUser();
        if (!this.passwordMatch(user, password)) throw new InvalidPermissionException(permittedRoles);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

        return true;
    }

}
