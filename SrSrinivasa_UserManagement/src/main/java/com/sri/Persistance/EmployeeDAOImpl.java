package com.sri.Persistance;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeDeletion;
import com.sri.Entity.OTPTable;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Repository.EmployeeDeletionRepository;
import com.sri.Repository.EmployeeRepository;
import com.sri.Repository.OtpTableRepository;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	OtpTableRepository otpTableRepository;
	
	@Autowired
	EmployeeDeletionRepository employeeDeletionRepository;
	
	@Override
	public String addEmployee(Employee emp) throws SQLException {
		if(empRepo.existsById(emp.getEmail())) {
			throw new SQLException();
		}
		String msg=empRepo.save(emp).getEmail();
		return "Employee added into Database with email : "+msg;
	}

	@Override
	public String addAllEmployee(List<Employee> empList) {
		empRepo.saveAll(empList);
 		return null;
	}
	
	@Override
	public String addExistingEmployee(Employee emp) {
 		return empRepo.save(emp).getEmail();
	}
	
	@Override
	public Employee getEmployee(String email) throws EmployeeNotFoundException  {
 		return empRepo.findByEmailId(email).orElseThrow(()->new EmployeeNotFoundException("Employee Not Found in Database"));
	}

	@Override
	public String updateEmployee(Employee emp) throws EmployeeNotFoundException {
		if(empRepo.existsById(emp.getEmail())&&empRepo.isEmployeeEnabled(emp.getEmail())) {
			
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
		if(empRepo.existsById(email)&&empRepo.isEmployeeEnabled(email)) {
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
	public String getEmployeeDepartmentbyEmail(String email) {
 		return empRepo.getEmployeeDepartmentbyEmail(email);
	}
	
	@Override
	public Page<Employee> findByDepartment(String department, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndEnabled(department,true, pageable);
	}

	@Override
	public Page<Employee> findByRoles(Set<String> roles, Pageable pageable) {
		 
		return empRepo.findByRoles(roles, pageable);
	}
	@Override
	public Page<Employee> getAllEmployees(Pageable pageable){
		return empRepo.findByEnabled(true,pageable);
	}

	@Override
	public Page<Employee> findByPhoneNumber(String phoneNumber, Pageable pageable) {
		return empRepo.findByPhoneNumberAndEnabled(phoneNumber,true , pageable);
	}

	@Override
	public Page<Employee> findByAddharNo(String addharNo, Pageable pageable) {
		 
		return empRepo.findByAddharNoAndEnabled(addharNo, true ,pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndRoles(String department, Set<String> roles, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndRoles(department, roles, pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndEmail(String department, String email, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndEmailAndEnabled(department, email,true , pageable);
	}

	@Override
	public Page<Employee> findByDepartmentAndPhoneNumber(String department, String phoneNumber, Pageable pageable) {
		 
		return empRepo.findByDepartmentAndPhoneNumberAndEnabled(department, phoneNumber,true , pageable);
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
		 
		return empRepo.findByEmailAndEnabled(email,true ,pageable);
	}

	@Override
	public boolean isEmployeeExist(String email) {
		 
		return empRepo.existsById(email)&&empRepo.isEmployeeEnabled(email);
	}

	@Override
	public boolean isEmployeeEnabled(String email) {
 		return empRepo.isEmployeeEnabled(email);
	}

	@Override
	public List<String> getEmailsBasedOnRole(Set<String> roles) {
 		return empRepo.getEmailsBasedOnRole(roles);
	}

	@Override
	public List<Employee> findEmployeesByRoles(Set<String> roles) {
 		return empRepo.findEmployeesByRoles(roles);
	}

	@Override
	public List<String> findByDepartment(String department) {
 		return empRepo.findEmailByDepartment(department);
	}

	@Override
	public void setregisteredTeamWithEmail(String registeredTeam, String email) {
		empRepo.setregisteredTeamWithEmail(registeredTeam, email);
	}

	@Override
	public void setRemoveRegisteredTeamWithEmail(String email) {
		empRepo.setRemoveRegisteredTeamWithEmail(email);
	}

	@Override
	public int updateEmployeeEnabledToTrue(String email) {
		return empRepo.updateEmployeeEnabledToTrue(true,email);
	}
	
	@Override
	public int updateEmpReJoinedToTrue(String email) {
 		return empRepo.updateEmpReJoinedToTrue(true, email);
	}

	
	@Override
	public String addOTP(OTPTable otpTable) {
 		return otpTableRepository.save(otpTable).getEmail();
	}

	@Override
	public OTPTable getOTP(String email) throws EmployeeNotFoundException {
		if(!otpTableRepository.existsById(email))
			throw new EmployeeNotFoundException("");
		return otpTableRepository.findById(email).get();
	}

	@Override
	public String deleteOTP(String email) {
		otpTableRepository.deleteById(email);
		return "Deleted";
	}

	@Override
	public boolean isEmployeeOldWorker(String email) {
		if(!empRepo.existsById(email)) {
			return false;
		}else if(!empRepo.isEmployeeEnabled(email)) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<Employee> getOldEmployeedetais(String email) {
 		return empRepo.getOldEmployeedetais(email);
	}

	@Override
	public int changeRole(Set<String> roles, String email) throws EmployeeNotFoundException {
		if(!empRepo.existsById(email)&&!empRepo.isEmployeeEnabled(email)) 
			throw new EmployeeNotFoundException(email);
		Employee emp=empRepo.findById(email).get();
		emp.setRoles(roles);
		empRepo.save(emp);
		return 1;
	}

	@Override
	public int changeDepartment(String department, String email) throws EmployeeNotFoundException {
		return empRepo.updateEmpDepartment(department, email);
	}

	@Override
	public Date getEmployeeDateOfJoiningbyEmail(String email) {
 		return empRepo.getEmployeeDateOfJoiningbyEmail(email);
	}
	
	@Override
	public Page<EmployeeDeletion> findDeletionEmpByEmail(String email, Pageable pg) {
 		return employeeDeletionRepository.findByEmail(email, pg);
	}

	@Override
	public Page<EmployeeDeletion> findDeletionEmpByDepartment(String department, Pageable pg) {
 		return employeeDeletionRepository.findByDepartment(department, pg);
	}

	@Override
	public Page<EmployeeDeletion> findDeletionEmpByStatus(String status, Pageable pg) {
 		return employeeDeletionRepository.findByStatus(status, pg);
	}

	@Override
	public Page<EmployeeDeletion> findDeletionEmpByDepartmentAndStatus(String department, String status, Pageable pg) {
 		return employeeDeletionRepository.findByDepartmentAndStatus(department, status, pg);
	}

	@Override
	public Page<EmployeeDeletion> findAllDeletionEmp(Pageable pg) {
 		return employeeDeletionRepository.findAll(pg);
	}

	@Override
	public String addEmployeeDeletion(EmployeeDeletion deletion) {
 		return employeeDeletionRepository.save(deletion).getEmployeeDeletionId()+"";
	}

	@Override
	public List<EmployeeDeletion> getEmployeeDeletion(String email)  {
		
 		return employeeDeletionRepository.findByEmail(email);
	}

	@Override
	public Page<EmployeeDeletion> findByEmployeeDeletionId(Long employeeDeletionId, Pageable pg) {
 		return employeeDeletionRepository.findByEmployeeDeletionId(employeeDeletionId, pg);
	}

	@Override
	public int upadteEmployeeDeletionStatusbyEmail(String status, Long employeeDeletionId) {
 		return employeeDeletionRepository.upadteEmployeeDeletionStatusbyEmail(status, employeeDeletionId);
	}

	@Override
	public int upadteEmployeeDeletionRequestedBybyEmail(Date requestedDate, String requestedBy,
			Long employeeDeletionId) {
 		return employeeDeletionRepository.upadteEmployeeDeletionRequestedBybyEmail(requestedDate, requestedBy, employeeDeletionId);
	}

	@Override
	public int upadteEmployeeDeletionApprovedBybyEmail(Date approvedDate, String requestedBy, Long employeeDeletionId) {
 		return employeeDeletionRepository.upadteEmployeeDeletionApprovedBybyEmail(approvedDate, requestedBy, employeeDeletionId);
	}

	@Override
	public EmployeeDeletion getEmployeeDeletionbyId(Long id) throws EmployeeNotFoundException {
		if(!employeeDeletionRepository.existsById(id))
			throw new EmployeeNotFoundException("");
		return employeeDeletionRepository.findById(id).get();
	}

	@Override
	public void setRemoveRegisteredTeamwithDepartmentAndRegisteredTeam(String department, String registeredTeam) {
		empRepo.setRemoveRegisteredTeamwithDepartmentAndRegisteredTeam(department, registeredTeam);
		}

}
