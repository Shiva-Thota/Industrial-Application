package com.sri.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
@SQLDelete(sql="update Employee set enabled=false where email=?")
@Where(clause="enabled<>false")
public class Employee implements Serializable{
	
	@Id
	@Column(unique=true)
	private String email;
	private String firstName;
	private String lastName;
	private  String father;
	private  String department;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private String gender;
	private String phoneNumber;
	@Column(length = 700)
	private String address;
	@Column(length = 75)
	private String password;
	private Date dateOfJoining;
	private  boolean enabled=true;
	
	private String bloodGroup;
	private String emergencyPerson;
	private String emergencyContact;
	private String maritalStatus;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] photo;
	
	private String addharNo;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] addharCard;
	
	@ElementCollection(fetch =FetchType.EAGER)
	@CollectionTable(name = "Employee_Roles",
			joinColumns=@JoinColumn(name = "email",referencedColumnName = "email"))
	@Column(name = "role")
	private Set<String> roles;
	
	
}
