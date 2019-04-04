package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.models.NotificationMessage;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private EmailService emailService;
	
	
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
	
	public void createNotification(String notificationType, Long notificationTypeId, String message, String originUserName,
									User user, Long instituionId, Long gradeId, Long subjectId, Long discussionId) {
		Notification notification = new Notification(notificationType, notificationTypeId, message, originUserName, user, instituionId, gradeId, subjectId, discussionId);
		notificationRepository.save(notification);
		emailService.sendNotificationEmail(notification);
	}

	public Notification readNotification(Long id) {
		Notification notification = findNotificationById(id);

		notification.setRead(true);
		notificationRepository.save(notification);
		return notification;
	}
}
