package com.sri.Entity;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.Multipart;
import lombok.Data;

@Data
public class EmployeeModel {
	

	private String email;
	private String firstName;
	private String lastName;
	private String father;
	private String department;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private String gender;
	private String phoneNumber;
	private String address;
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfJoining;
	private boolean enabled=true;
	private MultipartFile photo;
	private String addharNo;
	private Set<String> roles;
	private String bloodGroup;
	private String emergencyPerson;
	private String emergencyContact;
	private MultipartFile addharCard;
	private String maritalStatus;
}
