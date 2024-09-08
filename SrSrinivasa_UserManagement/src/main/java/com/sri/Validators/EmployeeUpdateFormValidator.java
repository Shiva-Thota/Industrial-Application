package com.sri.Validators;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sri.Entity.EmployeeModel;

@Component
public class EmployeeUpdateFormValidator implements Validator{

	private static final List<String> SUPPORTED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg");
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(EmployeeModel.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		EmployeeModel emp=(EmployeeModel) target;
		
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
		
		if(emp.getGender()==null ||emp.getGender().isBlank()) {
			errors.rejectValue("gender", "emp.gender.null");
		}
		
		if(emp.getDateOfBirth()==null) {
			errors.rejectValue("dateOfBirth", "emp.dateOfBirth.null");
		}
		
		if(emp.getPhoneNumber()==null ||emp.getPhoneNumber().isBlank()) {
			errors.rejectValue("phoneNumber", "emp.phoneNumber.null");
		}else if(!isValidPhone(emp.getPhoneNumber())) {
			errors.rejectValue("phoneNumber", "emp.phoneNumber.format");
		}
		
		if(emp.getFather()==null ||emp.getFather().isBlank()) {
			errors.rejectValue("father", "emp.father.null");
		}
		
		if(emp.getAddharCard()==null ) {
			errors.rejectValue("addharCard", "emp.addharCard.null");
		}else if(!SUPPORTED_CONTENT_TYPES.contains(emp.getAddharCard().getContentType())) {
			errors.rejectValue("addharCard", "emp.addharCard.UnSupportedFromat");
		}
		
		if(emp.getPhoto()==null) {
			errors.rejectValue("photo", "emp.photo.null");
		}else if(!SUPPORTED_CONTENT_TYPES.contains(emp.getPhoto().getContentType())) {
			errors.rejectValue("photo", "emp.photo.UnSupportedFromat");
		}
		
		if(emp.getBloodGroup()==null ||emp.getBloodGroup().isBlank()) {
			errors.rejectValue("bloodGroup", "emp.bloodGroup.null");
		}
		
		if(emp.getEmergencyContact()==null ||emp.getEmergencyContact().isBlank()) {
			errors.rejectValue("emergencyContact", "emp.emergencyContact.null");
		}
		
		if(emp.getEmergencyPerson()==null ||emp.getEmergencyPerson().isBlank()) {
			errors.rejectValue("emergencyPerson", "emp.emergencyPerson.null");
		}
		
		if(emp.getMaritalStatus()==null ||emp.getMaritalStatus().isBlank()) {
			errors.rejectValue("maritalStatus", "emp.maritalStatus.null");
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
