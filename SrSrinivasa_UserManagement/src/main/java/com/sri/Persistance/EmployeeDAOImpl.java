package com.sri.Persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sri.Entity.Employee;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Repository.EmployeeRepository;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

	@Autowired
	EmployeeRepository empRepo;
	
	@Override
	public String addEmployee(Employee emp) throws SQLException {
		if(empRepo.existsById(emp.getEmail())) {
			throw new SQLException();
		}
		String msg=empRepo.save(emp).getEmail();
		return "Employee added into Database with email : "+msg;
	}

	@Override
	public Employee getEmployee(String email) throws EmployeeNotFoundException  {
		
		return empRepo.findById(email).orElseThrow(()->new EmployeeNotFoundException("Employee Not Found in Database"));
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
	public String deleteEmployee(String email) throws EmployeeNotFoundException {
		if(empRepo.existsById(email)) {
			empRepo.deleteById(email);
			return email+" Deleted ";
		}else {
			throw new EmployeeNotFoundException("Employee Not Found in Database");
		}
		
	}
	

	@Override
	public String getPasswordWithEmail(String email) {
		 
		return empRepo.getPasswordWithEmail(email);
	}

	@Override
	public byte[] getPhotoWithEmail(String email) {
		 
		return empRepo.getPhotoWithEmail(email);
	}

	@Override
	public String getFullNameWithEmail(String email) {
		 
		return empRepo.getFullNameWithEmail(email);
	}

	@Override
	public String setPasswordWithEmail(String password,String email) {
		 
		empRepo.setPasswordWithEmail(password,email);
		return "Password Updated";
	}

	@Override
	public Page<Employee> findByDepartment(String department, Pageable pageable) {
		 
		return empRepo.findByDepartment(department, pageable);
	}

	@Override
	public Page<Employee> findByRoles(Set<String> roles, Pageable pageable) {
		 
		return empRepo.findByRoles(roles, pageable);
	}
	@Override
	public Page<Employee> getAllEmployees(Pageable pageable){
		return empRepo.findAll(pageable);
	}

	@Override
	public Page<Employee> findByPhoneNumber(String phoneNumber, Pageable pageable) {
		return empRepo.findByPhoneNumber(phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByAddharNo(String addharNo, Pageable pageable) {
		 
		return empRepo.findByAddharNo(addharNo, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRoles(String department, Set<String> roles, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndRoles(department, roles, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndEmail(String department, String email, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndEmail(department, email, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndPhoneNumber(String department, String phoneNumber, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndPhoneNumber(department, phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByRolesAndEmail(Set<String> roles, String email, Pageable pageable) {
		 
		return empRepo.findByRolesAndEmail(roles, email, pageable);
	}

	@Override
	public Page<Employee> findByRolesAndPhoneNumber(Set<String> roles, String phoneNumber, Pageable pageable) {
		 
		return empRepo.findByRolesAndPhoneNumber(roles, phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRolesAndEmail(String department, Set<String> roles, String email,
			Pageable pageable) {
		 
		return empRepo.findByDepartmentAndRolesAndEmail(department, roles, email, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRolesAndPhoneNumber(String department, Set<String> roles,
			String phoneNumber, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndRolesAndPhoneNumber(department, roles, phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByEmail(String email, Pageable pageable) {
		 
		return empRepo.findByEmail(email, pageable);
	}

	@Override
	public boolean isEmployeeExist(String email) {
		 
		return empRepo.existsById(email);
	}

	@Override
	public boolean isEmployeeEnabled(String email) {
		// TODO Auto-generated method stub
		return empRepo.isEmployeeEnabled(email);
	}

}
