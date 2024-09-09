package com.sri.Service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.passwordNotMatchedException;

public interface EmployeeService extends UserDetailsService{

	String addEmployee(Employee emp) throws SQLException;
	Employee getEmployee(String email) throws EmployeeNotFoundException;
	String updateEmployee(EmployeeModel emp) throws EmployeeNotFoundException;
	String deleteEmployee(String email) throws EmployeeNotFoundException;
	
	EmployeeModel getEmployeeModel(String email) throws EmployeeNotFoundException;

	byte[] getPhotoWithEmail(String email);
	String getFullNameWithEmail(String email);
	List<String> getRolesWithEmail(String email);
	String setPasswordWithEmail(String email,String oldPswrd,String newPswrd) throws passwordNotMatchedException;
	Page<Employee> getAllEmployees(String department, String role, String email, String phone, Pageable pageable);
	boolean isEmployeeExist(String email);
	 
}
