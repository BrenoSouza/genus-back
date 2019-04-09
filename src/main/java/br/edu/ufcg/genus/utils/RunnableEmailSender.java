package br.edu.ufcg.genus.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class RunnableEmailSender implements Runnable {
	
	private SimpleMailMessage message;
	
	@Autowired
    private JavaMailSender emailSender;
	
	public RunnableEmailSender(SimpleMailMessage message) {
		this.message = message;
	}

	@Override
	public void run() {
		this.emailSender.send(this.message);
	}

}
