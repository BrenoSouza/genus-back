package br.edu.ufcg.genus.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.DiscussionCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.models.NotificationMessage;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.NotificationRepository;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	/**
	 * Send notification to users subscribed on channel "/user/queue/notify".
	 *
	 * The message will be sent only to the user with the given username.
	 * 
	 * @param notification The notification message.
	 * @param username The username for the user to send notification.
	 */
	public void notify(NotificationMessage notification, String username) {
	  messagingTemplate.convertAndSendToUser(
		username, 
		"/queue/notify", 
		notification
	  );
	  return;
	}

	public Notification findNotificationById(Long id) {
		return notificationRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Notification with passed ID was not found", id));
	}
	
	public void createNotification(String notificationType, Long notificationTypeId, String message, User user) {
		Notification notification = new Notification(notificationType, notificationTypeId, message, user);
		notificationRepository.save(notification);
	}
}
