package br.edu.ufcg.genus.notification_controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import br.edu.ufcg.genus.models.Notification;
import br.edu.ufcg.genus.models.NotificationMessage;

@Controller
public class NotificationController {

    // @MessageMapping("/notification")
    // @SendTo("/topic/greetings")
    // public NotificationMessage greeting(Notification notification) throws Exception {
    //     Thread.sleep(3000); // simulated delay
    //     return new NotificationMessage("Novo post " + notification.getMessage() + "!");
    // }
}