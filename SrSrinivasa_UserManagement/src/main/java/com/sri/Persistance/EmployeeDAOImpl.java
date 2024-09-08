package com.sri.Persistance;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<Employee> findByDepartment(String department, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByDepartment(department, pageable);
	}

	@Override
	public Page<Employee> findByRoles(Set<String> roles, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByRoles(roles, pageable);
	}
	@Override
	public Page<Employee> getAllEmployees(Pageable pageable){
		return empRepo.findAll(pageable);
	}

	@Override
	public Page<Employee> findByPhoneNumber(String phoneNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByPhoneNumber(phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByAddharNo(String addharNo, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByAddharNo(addharNo, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRoles(String department, Set<String> roles, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByDepartmentAndRoles(department, roles, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndEmail(String department, String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByDepartmentAndEmail(department, email, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndPhoneNumber(String department, String phoneNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByDepartmentAndPhoneNumber(department, phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByRolesAndEmail(Set<String> roles, String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByRolesAndEmail(roles, email, pageable);
	}

	@Override
	public Page<Employee> findByRolesAndPhoneNumber(Set<String> roles, String phoneNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByRolesAndPhoneNumber(roles, phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRolesAndEmail(String department, Set<String> roles, String email,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByDepartmentAndRolesAndEmail(department, roles, email, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRolesAndPhoneNumber(String department, Set<String> roles,
			String phoneNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByDepartmentAndRolesAndPhoneNumber(department, roles, phoneNumber, pageable);
	}

	@Override
	public Page<Employee> findByEmail(String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return empRepo.findByEmail(email, pageable);
	}

}
