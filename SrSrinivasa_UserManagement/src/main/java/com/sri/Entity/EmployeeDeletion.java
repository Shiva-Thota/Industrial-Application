package com.sri.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class EmployeeDeletion {

	@Id
	@SequenceGenerator(allocationSize = 1,name = "EmployeeDeletionSequence")
	@GeneratedValue(generator = "EmployeeDeletionSequence")
	private Long employeeDeletionId;
	
	private String email;
	
	private String name;
	private String department;
	private String status;
	@Column(length = 2000)
	private String reasonForDeletion;
 	private String requestedBy;
	private String approvedBy;
	private Date requestedDate;
	private Date approvedDate;
	private Date joinedDate;
}
