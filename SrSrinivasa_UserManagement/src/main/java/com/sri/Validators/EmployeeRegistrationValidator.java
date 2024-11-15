package com.sri.Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sri.Entity.Employee;
import com.sri.Service.EmployeeService;

@Component
public class EmployeeRegistrationValidator implements Validator{
	
	@Autowired
	EmployeeService empSer;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Employee.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Employee emp=(Employee) target;
		
		if(emp.getFirstName()==null|| emp.getFirstName().isBlank()) {
			errors.rejectValue("firstName", "emp.firstName.null");
		}else if(emp.getFirstName().length()<4) {
			errors.rejectValue("firstName", "emp.firstName.length");
		}
		
		if(emp.getLastName()==null|| emp.getLastName().isBlank()) {
			errors.rejectValue("lastName", "emp.lastName.null");
		}else if(emp.getLastName().length()<4) {
			errors.rejectValue("lastName", "emp.lastName.length");
		}
		
		if(emp.getDepartment()==null ||emp.getDepartment().isBlank()) {
			errors.rejectValue("department", "emp.department.null");
		}
		
		if(emp.getAddharNo()==null ||emp.getAddharNo().isBlank()) {
			errors.rejectValue("addharNo", "emp.addharNo.null");
		}
		if(emp.getGender()==null ||emp.getGender().isBlank()) {
			errors.rejectValue("gender", "emp.gender.null");
		}
		if(emp.getRoles()==null ) {
			errors.rejectValue("role", "emp.role.null");
		}
		
		if(emp.getDateOfBirth()==null) {
			errors.rejectValue("dateOfBirth", "emp.dateOfBirth.null");
		}
		
		if(emp.getCapabilities()==null ||emp.getCapabilities().isBlank()) {
			errors.rejectValue("capabilities", "emp.capabilities.null");
		}
		
		
		if(emp.getEmail()==null ||emp.getEmail().isBlank()) {
			errors.rejectValue("email", "emp.email.null");
		}else if(!isValidEmail(emp.getEmail())) {
			errors.rejectValue("email", "emp.email.format");
		}else if(empSer.isEmployeeExist(emp.getEmail())) {
			errors.rejectValue("email", "emp.email.alreadyExist");
		}
		
		if(emp.getPhoneNumber()==null ||emp.getPhoneNumber().isBlank()) {
			errors.rejectValue("phoneNumber", "emp.phoneNumber.null");
		}else if(!isValidPhone(emp.getPhoneNumber())) {
			errors.rejectValue("phoneNumber", "emp.phoneNumber.format");
		}
		
		
		
	}

	private boolean isValidEmail(String email) {
		Pattern p=Pattern.compile("[A-Za-z0-9][A-Za-z0-9_]*@gmail[.]com");
		Matcher m=p.matcher(email);
		if(m.find()&&m.group().equals(email)) {
			return true;
		}else {
			return false;
		}
	}

	private boolean isValidPhone(String phn) {
		Pattern p=Pattern.compile("[0|91]?[6-9][0-9]{9}");
		Matcher m=p.matcher(phn);
		if(m.find()&&m.group().equals(phn)) {
			return true;
		}else {
			return false;
		}
	}

}
