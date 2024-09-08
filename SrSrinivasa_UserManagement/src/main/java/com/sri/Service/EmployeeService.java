package com.sri.Service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.passwordNotMatchedException;

public interface EmployeeService extends UserDetailsService{

	String addEmployee(Employee emp);
	Employee getEmployee(String email) throws EmployeeNotFoundException;
	List<Employee> getAllEmployee();
	String updateEmployee(EmployeeModel emp) throws EmployeeNotFoundException;
	EmployeeModel getEmployeeModel(String email) throws EmployeeNotFoundException;

	byte[] getPhotoWithEmail(String email);
	String getFullNameWithEmail(String email);
	List<String> getRolesWithEmail(String email);
	String setPasswordWithEmail(String email,String oldPswrd,String newPswrd) throws passwordNotMatchedException;
	
	Page<Employee> findByDepartment(String department,Pageable pageable);
	Page<Employee> findByRoles(Set<String> roles,Pageable pageable);
	Page<Employee> getAllEmployees(Pageable pageable);
}
