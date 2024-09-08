package com.sri.Persistance;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sri.Entity.Employee;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Repository.EmployeeRepository;

@Component
public class EmployeeDAOImpl implements EmployeeDAO{

	@Autowired
	EmployeeRepository empRepo;
	
	@Override
	public String addEmployee(Employee emp) {
		String msg=empRepo.save(emp).getEmail();
		return "Employee added into Database with email : "+msg;
	}

	@Override
	public Employee getEmployee(String email) throws EmployeeNotFoundException  {
		
		return empRepo.findById(email).orElseThrow(()->new EmployeeNotFoundException("Employee Not Found in Database"));
	}

	@Override
	public List<Employee> getAllEmployee() {
		
		return (List<Employee>) empRepo.findAll();
	}

	@Override
	public String updateEmployee(Employee emp) throws EmployeeNotFoundException {
		if(empRepo.existsById(emp.getEmail())) {
			
			empRepo.updateEmployee(emp.getAddharCard(),emp.getAddress(),emp.getBloodGroup(),emp.getDateOfBirth(),
					emp.getEmergencyContact(),emp.getEmergencyPerson(),emp.getFather(),emp.getFirstName(),emp.getGender(),  
					emp.getLastName(),  emp.getMaritalStatus(),  emp.getPhoneNumber(),  emp.getPhoto(),emp.getAddharNo(), emp.getEmail());
			
			return "Employee updated into Database ";
		}else {
			throw new EmployeeNotFoundException("Employee Not Found in Database");
		}
	}

	@Override
	public List<String> getRolesWithEmail(String email) {
		return empRepo.getRolesWithEmail(email);
	}

	@Override
	public String getPasswordWithEmail(String email) {
		// TODO Auto-generated method stub
		return empRepo.getPasswordWithEmail(email);
	}

	@Override
	public byte[] getPhotoWithEmail(String email) {
		// TODO Auto-generated method stub
		return empRepo.getPhotoWithEmail(email);
	}

	@Override
	public String getFullNameWithEmail(String email) {
		// TODO Auto-generated method stub
		return empRepo.getFullNameWithEmail(email);
	}

	@Override
	public String setPasswordWithEmail(String password,String email) {
		// TODO Auto-generated method stub
		empRepo.setPasswordWithEmail(password,email);
		return "Password Updated";
	}

}
