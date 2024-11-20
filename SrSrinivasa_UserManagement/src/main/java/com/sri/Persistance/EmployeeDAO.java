package com.sri.Persistance;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeDeletion;
import com.sri.Entity.OTPTable;
import com.sri.Exceptions.EmployeeNotFoundException;

 public interface EmployeeDAO {

	String addEmployee(Employee emp) throws SQLException;
	String addExistingEmployee(Employee emp);
	Employee getEmployee(String email) throws EmployeeNotFoundException;
	String updateEmployee(Employee emp) throws EmployeeNotFoundException;
	String deleteEmployee(String email) throws EmployeeNotFoundException;

	
	List<String> getRolesWithEmail(String email);
	String getPasswordWithEmail(String email);
	void setregisteredTeamWithEmail(String registeredTeam,String email);	
	void setRemoveRegisteredTeamWithEmail(String email);

	
	int changeRole(Set<String> roles,String email)throws EmployeeNotFoundException;
	int changeDepartment(String department,String email)throws EmployeeNotFoundException;
	
	
	byte[] getPhotoWithEmail(String email);
	String getFullNameWithEmail(String email);
	String getEmployeeDepartmentbyEmail(String email);
	Date getEmployeeDateOfJoiningbyEmail(String email);
	String setPasswordWithEmail(String password,String email);
	boolean isEmployeeExist(String email);
	boolean isEmployeeOldWorker(String email);
	Optional<Employee> getOldEmployeedetais(String email);

	
	boolean isEmployeeEnabled(String email);
	int updateEmployeeEnabledToTrue(String email);
	
	List<String> getEmailsBasedOnRole(Set<String> roles);
	
	 List<Employee> findEmployeesByRoles(Set<String> roles);
     List<String> findByDepartment(String department);

	
	  Page<Employee> findByDepartment(String department,Pageable pageable);
	
	  Page<Employee> findByPhoneNumber(String phoneNumber,Pageable pageable);
	
	  Page<Employee> findByRoles(Set<String> roles,Pageable pageable);
	
	  Page<Employee> findByAddharNo(String addharNo,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndRoles(String department, Set<String> roles,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndEmail(String department, String email,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndPhoneNumber(String department, String phoneNumber,Pageable pageable);
	
	  Page<Employee> findByRolesAndEmail(Set<String> roles, String email,Pageable pageable);
	
	  Page<Employee> findByRolesAndPhoneNumber(Set<String> roles, String phoneNumber,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndRolesAndEmail(String department, Set<String> roles, String email,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndRolesAndPhoneNumber(String department, Set<String> roles, String phoneNumber,Pageable pageable);
	
	  Page<Employee> getAllEmployees(Pageable pageable);
	  
	  Page<Employee> findByEmail(String email,Pageable pageable);
	  
	  
	  //Deletion Repository
	  Page<EmployeeDeletion> findDeletionEmpByEmail(String email,Pageable pg);
	  Page<EmployeeDeletion> findDeletionEmpByDepartment(String department,Pageable pg);
	  Page<EmployeeDeletion> findDeletionEmpByStatus(String status,Pageable pg);
	  Page<EmployeeDeletion> findDeletionEmpByDepartmentAndStatus(String department, String status,Pageable pg);
	  Page<EmployeeDeletion> findAllDeletionEmp(Pageable pg);
	  Page<EmployeeDeletion> findByEmployeeDeletionId(Long employeeDeletionId,Pageable pg);
	
	  List<EmployeeDeletion> getEmployeeDeletion(String email);
	  	
	  String addEmployeeDeletion(EmployeeDeletion deletion);
	  EmployeeDeletion getEmployeeDeletionbyId(Long id) throws EmployeeNotFoundException;
	
	  int upadteEmployeeDeletionStatusbyEmail(String status,Long employeeDeletionId);
	  int upadteEmployeeDeletionRequestedBybyEmail(Date requestedDate,String requestedBy,Long employeeDeletionId);		
	  int upadteEmployeeDeletionApprovedBybyEmail(Date approvedDate,String requestedBy,Long employeeDeletionId);
	
      int updateEmpReJoinedToTrue(String email);

  	void setRemoveRegisteredTeamwithDepartmentAndRegisteredTeam(String department,String registeredTeam);

	  
	  //OTP table
	  String addOTP(OTPTable otpTable);
	  OTPTable getOTP(String email) throws EmployeeNotFoundException;
	  String deleteOTP(String email);
}
