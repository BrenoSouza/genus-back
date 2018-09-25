package br.edu.ufcg.genus.utils;

import java.util.List;

import br.edu.ufcg.genus.exception.InvalidPermissionException;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;

public class PermissionChecker {
	
	
	public static void checkPermission (User user, Long institutionId, List<UserRole> permitedRoles) {
		if (!permitedRoles.contains(user.getRole(institutionId))) {
			throw new InvalidPermissionException(permitedRoles);
		}
	}

}
