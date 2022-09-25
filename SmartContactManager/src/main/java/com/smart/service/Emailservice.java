package com.smart.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import org.springframework.stereotype.Service;

@Service
public class Emailservice {



	public boolean sendEmail(String subject, String message, String to) {
		boolean bf = false;
		
		String from ="htik4649@gmail.com";
		
		String host= "smtp.gmail.com";
		Properties properties= System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		javax.mail.Session session=  Session.getInstance(properties, new Authenticator(){
			protected PasswordAuthentication getpasswordauthentication( ) {
				return new PasswordAuthentication("htik4649@gmail.com","ashfaq786786");	
			}
		});	
		session.setDebug(true);
		MimeMessage m = new MimeMessage(session);
		try {
			m.setFrom(from);
			m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			m.setSubject(subject);
			Transport.send(m);
			bf=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bf;


	}










}
