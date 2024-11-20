package com.sri.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeDeletion;
import com.sri.Entity.EmployeeModel;
import com.sri.Entity.OTPTable;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.passwordNotMatchedException;

import jakarta.mail.MessagingException;

public interface EmployeeService extends UserDetailsService{

	String addEmployee(Employee emp) throws SQLException, MessagingException;
	Employee getEmployee(String email) throws EmployeeNotFoundException;
	String addOldEmployee(String email) throws EmployeeNotFoundException;
	String updateEmployee(EmployeeModel emp) throws EmployeeNotFoundException;
	String deleteEmployeeReq(String email, String requestedBy, String reasonForDeletion) throws EmployeeNotFoundException;
	List<String> getEmailsBasedOnRole(Set<String> roles);
	List<Employee> findEmployeesByRoles(Set<String> roles);
	List<String> findByDepartment(String department);
	void setregisteredTeamWithEmail(String registeredTeam,String email);
	void setRemoveRegisteredTeamWithEmail(String email);
	
	

	EmployeeModel getEmployeeModel(String email) throws EmployeeNotFoundException;
	String ForgotPasswordSendOTP(String email) throws MessagingException;
	String updateForgotPassword(String email,String password) throws EmployeeNotFoundException;

	byte[] getPhotoWithEmail(String email);
	String getFullNameWithEmail(String email);
	List<String> getRolesWithEmail(String email);
	String setPasswordWithEmail(String email,String oldPswrd,String newPswrd) throws passwordNotMatchedException;
	Page<Employee> getAllEmployees(String department, String role, String email, String phone, Pageable pageable);
	boolean isEmployeeExist(String email);
	boolean isEmployeeOldWorker(String email); 
	Optional<Employee> getOldEmployeedetais(String email);
	int changeRole(Set<String> roles,String email)throws EmployeeNotFoundException;
	int changeDepartment(String department,String email)throws EmployeeNotFoundException;
	
	//Deletion repository
	List<EmployeeDeletion> getEmployeeDeletion(String email);
  	String addEmployeeDeletion(EmployeeDeletion deletion);
  	String deleteEmployee(String email) throws EmployeeNotFoundException;
	Date getEmployeeDateOfJoiningbyEmail(String email);

  	int upadteEmployeeDeletionStatusbyEmail(String status,Long employeeDeletionId);
	int upadteEmployeeDeletionRequestedBybyEmail(Date requestedDate,String requestedBy,Long employeeDeletionId);		
	int upadteEmployeeDeletionApprovedBybyEmail(Date approvedDate,String requestedBy,Long employeeDeletionId);
	
	Page<EmployeeDeletion> getEmployeeDeletionPage(String email,Long employeeDeletionId,String department,String status,Pageable pg);
	EmployeeDeletion getEmployeeDeletionbyId(Long id) throws EmployeeNotFoundException;

    int updateEmpReJoinedToTrue(String email);

	
	//OTP
	String addOTP(OTPTable otpTable);
	OTPTable getOTP(String email) throws EmployeeNotFoundException;
	String deleteOTP(String email);
}
