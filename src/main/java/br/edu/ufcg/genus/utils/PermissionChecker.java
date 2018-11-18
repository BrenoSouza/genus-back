package br.edu.ufcg.genus.utils;

import java.util.List;

import br.edu.ufcg.genus.exception.InvalidPermissionException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;

public class PermissionChecker {
	
	
	public static void checkPermission (User user, Long institutionId, List<UserRole> permitedRoles) {
		if (!permitedRoles.contains(user.getRole(institutionId))) {
			throw new InvalidPermissionException(permitedRoles);
		}
	}
	
	public static void checkSubjectPermission(User user, Subject subject) {
		if (!user.checkStudent(subject) && !user.checkTeacher(subject)) {
			throw new NotAuthorizedException("You don't have permission to do this");
		}
	}
	
	public static void checkDiscussionPermission(User user, Discussion discussion) {
		Subject subject = discussion.getSubject();
		if (!user.checkTeacher(subject) && !discussion.getCreator().equals(user)) throw new NotAuthorizedException("You don't have permission to do this");
	}
	
	public static void checkReplyPermission(User user, Reply reply) {
		Subject subject = reply.getDiscussion().getSubject();
		if (!user.checkTeacher(subject) && !reply.getCreator().equals(user)) throw new NotAuthorizedException("You don't have permission to do this");
	}

}
