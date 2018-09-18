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

import br.edu.ufcg.genus.beans.AuthenticationBean;
import br.edu.ufcg.genus.beans.UserBean;
import br.edu.ufcg.genus.exception.InvalidTokenException;
import br.edu.ufcg.genus.models.User;
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
	
	public User createUser (UserBean userBean) {
		Set<ConstraintViolation<UserBean>> violations = validator.validate(userBean);

		if (violations.size() > 0) {
        	String errorString = "";
        	for (ConstraintViolation<UserBean> v : violations) {
        		errorString = " " + errorString + v.getMessage();
        	}
            throw new RuntimeException("Invalid attributes passed to creation of an user" + errorString);
        }
		User newUser = new User(userBean.getUsername(), userBean.getEmail(), passwordEncoder.encode(userBean.getPassword()));
		return this.userRepository.save(newUser);
	}

	public String login (AuthenticationBean authBean) {
        String email = authBean.getEmail();
        String password = authBean.getPassword();

		try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
            		.orElseThrow(() -> new RuntimeException("Invalid email or password", null));
            
            return jwtTokenProvider.createToken(email, user.getRoles());
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password", e);
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
}
