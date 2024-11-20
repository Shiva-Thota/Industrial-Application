package com.sri.mail;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SriSrinivasaMailSender {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	String SriSrinivasaIndustryMail;

	public String sendJoiningMail(String nameOfEmployee,String toMail,Set<String> roles,String password) throws MessagingException {
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true);
		messageHelper.setSentDate(new Date());
		messageHelper.setSubject("Welcome to Sri Srinivasa Industries");
		messageHelper.setText("Dear "+nameOfEmployee+",\r\n"
				+ "\r\n"
				+ "We are pleased to welcome you to Sri Srinivasa Industries. As you join our team, we want to ensure you have all the necessary information for a smooth start.\r\n"
				+ "\r\n"
				+ "Your Designated Role:\r\n"
				+ "Role: "+roles.toString()+"\r\n"
				+ "\r\n"
				+ "Account Information:\r\n"
				+ "Your account has been created, granting you access to our internal systems and resources.\r\n"
				+ "Please log in using the initial credentials provided below, and change your password upon first login to maintain account security.\r\n"
				+ "\r\n"
				+ "From Sri Srinivasa Industries\r\n"
				+ "Account created\r\n"
				+ "Change your password upon first login. Current password:"+password+"\r\n"
				+ "\r\n"
				+ "For any questions or support with your login or role setup, please feel free to reach out to our IT or HR departments. We’re here to support you as you begin this new chapter with us.\r\n"
				+ "\r\n"
				+ "Thank you, and welcome once again to Sri Srinivasa Industries.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "Sri Srinivasa Industries");
		messageHelper.setTo(toMail);
		messageHelper.setFrom(SriSrinivasaIndustryMail);
		
		mailSender.send(mimeMessage);
		return "Mail Sent";
	}
	
	public String sendForgotPasswordMail(String toMail,String OTP) throws MessagingException {
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true);
		messageHelper.setSentDate(new Date());
		messageHelper.setSubject(" Password Reset Request – Sri Srinivasa Industries");
		messageHelper.setText("Dear "+toMail+",\r\n"
				+ "\r\n"
				+ "We received a request to reset the password for your account. Please use the One-Time Password (OTP) provided below to proceed with resetting your password.\r\n"
				+ "\r\n"
				+ "Your OTP:"+OTP+"\r\n"
				+ "\r\n"
				+ "This OTP is valid for the next [time duration, e.g., 15 minutes]. If you did not initiate this request, please contact our IT support team immediately at [support contact details].\r\n"
				+ "\r\n"
				+ "For security reasons, do not share this OTP with anyone.\r\n"
				+ "\r\n"
				+ "Thank you for your cooperation.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "Sri Srinvasa Industries");
		messageHelper.setTo(toMail);
		messageHelper.setFrom(SriSrinivasaIndustryMail);
		
		mailSender.send(mimeMessage);
		return "Mail Sent";
		
	}
	
}
