package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private EmailService emailService;
	

	public Notification findNotificationById(Long id) {
		return notificationRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Notification with passed ID was not found", id));
	}
	
	public void createNotification(String notificationType, Long notificationTypeId, String message, String originUserName,
									List<User> usersToNotify, Long instituionId, Long gradeId, Long subjectId, Long discussionId) {
		List<String> emails = new ArrayList<>();
		for(User user: usersToNotify) {
			Notification notification = new Notification(notificationType, notificationTypeId, message, originUserName, user, instituionId, gradeId, subjectId, discussionId);
			notificationRepository.save(notification);
			emails.add(user.getEmail());
		}
		String convertedNotificationType = "Nova resposta em uma discussão";
		if(notificationType.equals("NEW_DISCUSSION")) convertedNotificationType = "Nova discussão";
		
		emailService.sendNotificationEmail(emails, convertedNotificationType, message);
	}

	public Notification readNotification(Long id) {
		Notification notification = findNotificationById(id);

		notification.setRead(true);
		notificationRepository.save(notification);
		return notification;
	}
}
