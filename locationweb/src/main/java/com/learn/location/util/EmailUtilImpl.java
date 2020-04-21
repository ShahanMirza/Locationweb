package com.learn.location.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailUtilImpl implements EmailUtil {

	@Autowired
	private JavaMailSender sender;
	@Override
	public void sendEmail(String toAddress, String subject, String body) {
		// TODO Auto-generated method stub
		MimeMessage createMimeMessage = sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(createMimeMessage);
		try {
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(body);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sender.send(createMimeMessage);

	}

}
