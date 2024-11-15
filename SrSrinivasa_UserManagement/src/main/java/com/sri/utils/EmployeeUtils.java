package com.sri.utils;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.sri.DTO.Prod_User_DTO_Employee;
import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeModel;

@Component
public class EmployeeUtils {


	public static EmployeeModel copyToEmployeeModel(Employee employee) {
		EmployeeModel employeeModel=new EmployeeModel();
 			employeeModel.setEmail(employee.getEmail());
 			employeeModel.setFirstName(employee.getFirstName());
 			employeeModel.setLastName(employee.getLastName());
 			employeeModel.setFather(employee.getFather());
 			employeeModel.setDepartment(employee.getDepartment());
 			employeeModel.setDateOfBirth(employee.getDateOfBirth());
 			employeeModel.setGender(employee.getGender());
 			employeeModel.setPhoneNumber(employee.getPhoneNumber());
 			employeeModel.setAddress(employee.getAddress());
 			employeeModel.setDateOfJoining(employee.getDateOfJoining());
 			employeeModel.setAddharNo(employee.getAddharNo());
 			employeeModel.setPassword(employee.getPassword());
		
 			employeeModel.setEnabled(employee.isEnabled());
		
 			employeeModel.setRoles(employee.getRoles());
 			employeeModel.setBloodGroup(employee.getBloodGroup());
 			employeeModel.setEmergencyContact(employee.getEmergencyContact());
 			employeeModel.setEmergencyPerson(employee.getEmergencyPerson());
 			employeeModel.setMaritalStatus(employee.getMaritalStatus());
		
		return employeeModel;
	}
	
	public static  Employee copyToEmployee(EmployeeModel employeeModel) {
		Employee employee=new Employee();
		
 			employee.setEmail(employeeModel.getEmail());
 			employee.setFirstName(employeeModel.getFirstName());
 			employee.setLastName(employeeModel.getLastName());
 			employee.setFather(employeeModel.getFather());
 			employee.setDepartment(employeeModel.getDepartment());
 			employee.setDateOfBirth(employeeModel.getDateOfBirth());
 			employee.setGender(employeeModel.getGender());
 			employee.setPhoneNumber(employeeModel.getPhoneNumber());
 			employee.setAddress(employeeModel.getAddress());
 			employee.setDateOfJoining(employeeModel.getDateOfJoining());
 			employee.setAddharNo(employeeModel.getAddharNo());
 			employee.setPassword(employeeModel.getPassword());
 			employee.setEnabled(employeeModel.isEnabled());
		
 			employee.setRoles(employeeModel.getRoles());
 			employee.setBloodGroup(employeeModel.getBloodGroup());
 			employee.setEmergencyContact(employeeModel.getEmergencyContact());
 			employee.setEmergencyPerson(employeeModel.getEmergencyPerson());
 			employee.setMaritalStatus(employeeModel.getMaritalStatus());
 			
 			try {
				employee.setPhoto(employeeModel.getPhoto().getInputStream().readAllBytes());
				employee.setAddharCard(employeeModel.getAddharCard().getInputStream().readAllBytes());
 			} catch (IOException e) {
				e.printStackTrace();
			}
 			
		
		return employee;
	}
	
	public static Prod_User_DTO_Employee EmployeeToProd_User_DTO(Employee emp) {
		
		Prod_User_DTO_Employee prodDTO=new Prod_User_DTO_Employee();
		prodDTO.setBloodGroup(emp.getBloodGroup());
		prodDTO.setCapabilities(emp.getCapabilities());
		prodDTO.setDateOfBirth(emp.getDateOfBirth());
		prodDTO.setDateOfJoining(emp.getDateOfJoining());
		prodDTO.setDepartment(emp.getDepartment());
		prodDTO.setEmail(emp.getEmail());
		prodDTO.setFullName(emp.getFirstName()+" "+emp.getLastName());
		prodDTO.setGender(emp.getGender());
		prodDTO.setPhoneNumber(emp.getPhoneNumber());
		prodDTO.setPhoto(emp.getPhoto());
		prodDTO.setRoles(emp.getRoles());
		prodDTO.setRegisteredTeam(emp.getRegisteredTeam());		
		return prodDTO;
	}
	public static boolean hasRole(Authentication authentication, String role) {
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream()
                             .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
        }
        return false;
    }
}












