package com.sri.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Prod_User_DTO_Employee implements Serializable{
	
	private String email;
	private String fullName;
 	private  String department;
 	private Date dateOfBirth;
	private String gender;
	private String phoneNumber;
 	private Date dateOfJoining;
 	private String bloodGroup;
  	private String capabilities;
  	private String registeredTeam;
  	private byte[] photo;
 	private Set<String> roles;
 	
	
}
