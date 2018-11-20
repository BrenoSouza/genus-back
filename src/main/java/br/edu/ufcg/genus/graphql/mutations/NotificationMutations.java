package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.services.NotificationService;

public class NotificationMutations implements GraphQLMutationResolver{
	
	@Autowired
	private NotificationService notificationService;
    
    public Notification readNotification(long notificationId) {
		return this.notificationService.readNotification(notificationId);
	}
	
}
