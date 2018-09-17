package br.edu.ufcg.genus.services;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.genus.beans.AuthenticationBean;
import br.edu.ufcg.genus.beans.UserBean;
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
		try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authBean.getUsername(), authBean.getPassword()));
            User user = userRepository.findByEmail(authBean.getEmail())
            		.orElseThrow(() -> new RuntimeException("Invalid username"));
            
            return jwtTokenProvider.createToken(authBean.getUsername(), user.getRoles());
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password", e);
        }
		
	}

	public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
	}
}
