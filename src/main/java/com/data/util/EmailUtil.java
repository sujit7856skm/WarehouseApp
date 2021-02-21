package com.data.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender sender;
	
	public boolean send(
			String to,
			String subject,
			String text
			)
	{
		boolean sent = false;
		try {
			// Creating MimeMessage object
			MimeMessage message = sender.createMimeMessage();
			
			// Creating MimeMessageHelper object
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			//Setting the data in the MimeMessage Object using helper object
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			
			//send mail
			sender.send(message);
			
			sent = true;
		} catch (Exception e) {
			sent = false;
			e.printStackTrace();
		}
		return sent;
	}

}
