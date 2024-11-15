package com.sri.Persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sri.Entity.Employee;
import com.sri.Exceptions.EmployeeNotFoundException;

 public interface EmployeeDAO {

	String addEmployee(Employee emp) throws SQLException;
	Employee getEmployee(String email) throws EmployeeNotFoundException;
	String updateEmployee(Employee emp) throws EmployeeNotFoundException;
	String deleteEmployee(String email) throws EmployeeNotFoundException;
	
	List<String> getRolesWithEmail(String email);
	String getPasswordWithEmail(String email);
	void setregisteredTeamWithEmail(String registeredTeam,String email);	
	void setRemoveRegisteredTeamWithEmail(String email);

	
	byte[] getPhotoWithEmail(String email);
	String getFullNameWithEmail(String email);
	String setPasswordWithEmail(String password,String email);
	boolean isEmployeeExist(String email);
	
	boolean isEmployeeEnabled(String email);
	
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
}
