package com.sri.utils;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
	
}
