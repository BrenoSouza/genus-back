package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.services.GradeService;
import br.edu.ufcg.genus.services.NotificationService;

public class NotificationQueries implements GraphQLQueryResolver {
	
	@Autowired
	private NotificationService notificationService;

	public Notification findNotification(long notificationId) {
		return notificationService.findNotificationById(notificationId);
	}
}
