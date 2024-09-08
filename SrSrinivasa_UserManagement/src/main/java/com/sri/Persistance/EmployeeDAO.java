package com.sri.Persistance;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sri.Entity.Employee;
import com.sri.Exceptions.EmployeeNotFoundException;

public interface EmployeeDAO {

	String addEmployee(Employee emp);
	Employee getEmployee(String email) throws EmployeeNotFoundException;
	List<Employee> getAllEmployee();
	String updateEmployee(Employee emp) throws EmployeeNotFoundException;
	List<String> getRolesWithEmail(String email);
	String getPasswordWithEmail(String email);
	
	byte[] getPhotoWithEmail(String email);
	String getFullNameWithEmail(String email);
	String setPasswordWithEmail(String password,String email);
	
	Page<Employee> findByDepartment(String department,Pageable pageable);
	Page<Employee> findByRoles(Set<String> roles,Pageable pageable);
	Page<Employee> getAllEmployees(Pageable pageable);
}
