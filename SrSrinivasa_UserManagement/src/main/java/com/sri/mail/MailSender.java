package com.sri.mail;

import org.springframework.stereotype.Component;

@Component
public class MailSender {

	public String sendmail(String mail,String message) {
		System.out.println("Mail Sent -- with password : "+message+"");
		return null;
		
	}
	
}
