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
import br.edu.ufcg.genus.models.Role;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.repositories.UserRepository;
import br.edu.ufcg.genus.security.JwtTokenProvider;
import br.edu.ufcg.genus.update_inputs.UpdateUserInput;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
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

    public Subject addTeacher(Long subjectId, Long teacherId) {
        User teacher = this.userRepository.findById(teacherId)
            .orElseThrow(() -> new InvalidIDException("Teacher with passed ID was not found", teacherId));

        Subject subject = this.subjectService.findSubjectById(subjectId);

        teacher.addSubject(subject);
        subject.addTeacher(teacher);

        this.subjectRepository.save(subject);
        return this.subjectService.findSubjectById(subjectId);
    }

    public Subject addStudent(Long subjectId, Long studentId) {
		List<UserRole> permittedRoles = new ArrayList<>();
		permittedRoles.add(UserRole.ADMIN);

        User student = this.userRepository.findById(studentId)
            .orElseThrow(() -> new InvalidIDException("Student with passed ID was not found", studentId));

        Subject subject = this.subjectService.findSubjectById(subjectId);
        Institution institution = subject.getGrade().getInstitution();

        if (!findRole(institution.getId()).equals(UserRole.ADMIN)) throw new InvalidPermissionException(permittedRoles);

        student.addSubjectStudent(subject);
        subject.addStudent(student);

        this.subjectRepository.save(subject);
        return this.subjectService.findSubjectById(subjectId);
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
    
    public User updateUser(UpdateUserInput input) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new InvalidTokenException("Token is not valid"));

        if (input.getUsername() != null) {
            user.setUsername(input.getUsername());
        }

        return userRepository.save(user);
    }

    public boolean updateUserPassword(String password, String newPassword) {
		List<UserRole> permittedRoles = new ArrayList<>();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidTokenException("User with passed Email was not found"));
        
        if (!this.passwordMatch(user, password)) throw new InvalidPermissionException(permittedRoles);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

        return true;
    }

}
