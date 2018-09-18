package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.beans.AuthenticationBean;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.services.UserService;

public class UserQueries implements GraphQLQueryResolver {
	
	@Autowired
	UserService userService;
	
	public String login (AuthenticationBean authBean) {
		return userService.login(authBean);		
	}

    public User findUser(long id) {
        return userService.findUserById(id).orElse(null);
    }

    public User findLoggedUser() {
        return userService.findLoggedUser();
    }

}
