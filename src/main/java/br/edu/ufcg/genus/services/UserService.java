package br.edu.ufcg.genus.services;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.beans.AuthenticationBean;
import br.edu.ufcg.genus.beans.UserBean;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private Validator validator;
	
	public User createUser (UserBean userBean) {
		Set<ConstraintViolation<UserBean>> violations = validator.validate(userBean);
		//TODO Use the error messages on the exception
        if (violations.size() > 0) {
        	String errorString = "";
        	for (ConstraintViolation<UserBean> v : violations) {
        		errorString = " " + errorString + v.getMessage();
        	}
            throw new RuntimeException("Invalid attributes passed to creation of an user" + errorString);
        }
		User newUser = new User(userBean.getUsername(), userBean.getEmail(), userBean.getPassword());
		return this.userRepository.save(newUser);
	}
	
	public User login (AuthenticationBean authBean) {
		User user = userRepository.findByUsername(authBean.getUsername());
		//improve this part
		if (user == null) throw new RuntimeException("Error on login");
		if (user.getPassword().equals(authBean.getPassword())) {
			return user;
		} else {
			throw new RuntimeException("Error on login");
		}
		
	}

}
