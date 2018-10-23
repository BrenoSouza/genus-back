package br.edu.ufcg.genus.graphql.queries;

import java.util.Arrays;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.UserRole;

public class UserRoleQueries implements GraphQLQueryResolver {
	
	public List<UserRole> getAllUserRoles() {
		return Arrays.asList(UserRole.values());
	}

}
