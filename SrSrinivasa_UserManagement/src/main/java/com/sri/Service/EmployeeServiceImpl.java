package com.sri.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeDeletion;
import com.sri.Entity.EmployeeModel;
import com.sri.Entity.OTPTable;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.passwordNotMatchedException;
import com.sri.Messaging.MessageProducer;
import com.sri.Persistance.EmployeeDAO;
import com.sri.mail.SriSrinivasaMailSender;
import com.sri.utils.EmployeeUtils;

import jakarta.mail.MessagingException;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeDAO empDao;
	
	@Autowired
	SriSrinivasaMailSender mailSender;
	
	@Autowired
	MessageProducer messageProducer;
	
	@Value("${Password_Mail_Text}")
	String passwordMailText;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public String addEmployee(Employee emp) throws SQLException, MessagingException {
		 
		//setting the random password
		char[] ch=new char[10];
		for(int i=0;i<10;i++) {
			ch[i]=(char)new Random().nextInt(65,123);
		}
		String password=String.valueOf(ch);
		emp.setPassword(passwordEncoder.encode(password));
		
		//sending password to the mail
//		mailSender.sendJoiningMail(emp.getFirstName()+" "+emp.getLastName(),emp.getEmail(),emp.getRoles(),password);

		//setting todays date
		emp.setDateOfJoining(new Date());		
		
		return empDao.addEmployee(emp);
	}

	@Override
	public String addOldEmployee(String email) throws EmployeeNotFoundException {
		//setting the random password
		char[] ch=new char[10];
		for(int i=0;i<10;i++) {
			ch[i]=(char)new Random().nextInt(65,123);
		}
		String password=String.valueOf(ch);				
 		//sending password to the mail
//				mailSender.sendJoiningMail(emp.getFirstName()+" "+emp.getLastName(),emp.getEmail(),emp.getRoles(),password);
		Optional<Employee> oldEmp=empDao.getOldEmployeedetais(email);
 		if(oldEmp.isEmpty())
			throw new EmployeeNotFoundException(email);
 		Employee emp=oldEmp.get();
 		if(empDao.isEmployeeOldWorker(email)) {
 			emp.setPassword(passwordEncoder.encode(password));
 			emp.setDateOfJoining(new Date());
 			emp.setReJoined(true);
 			emp.setRegisteredTeam(null);
  			 empDao.addExistingEmployee(emp);
		}
 		empDao.updateEmployeeEnabledToTrue(email);
 		return email;
	}
	
	@Override
	public Employee getEmployee(String email) throws EmployeeNotFoundException {
		 
		return empDao.getEmployee(email);
	}


	@Override
	public String updateEmployee(EmployeeModel empModel) throws EmployeeNotFoundException {
		//coping the EmployeeModel Data to Employee Object Data
		Employee employee=EmployeeUtils.copyToEmployee(empModel);
		return empDao.updateEmployee(employee);
	}

	@Override
	public EmployeeModel getEmployeeModel(String email) throws EmployeeNotFoundException {
		Employee emp=empDao.getEmployee(email);
		//coping the Employee data to EmployeeModel Object
		EmployeeModel empModel=EmployeeUtils.copyToEmployeeModel(emp);
		
		return empModel;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Collecting password and roles from Database
		String password=empDao.getPasswordWithEmail(username);
		List<String> roles=empDao.getRolesWithEmail(username);

		Set<SimpleGrantedAuthority> authorities=new HashSet<SimpleGrantedAuthority>();
		//generating SimpleGranted Authority from roles set
		roles.stream().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role));
		});
		boolean isEnabled=empDao.isEmployeeEnabled(username);
		User usr=new User(username, password, isEnabled, true, true, true, authorities);
		//User user=new User(username, password, authorities);
		return usr;
	}

	@Override
	public byte[] getPhotoWithEmail(String email) {
		 
		return empDao.getPhotoWithEmail(email);
	}

	@Override
	public String getFullNameWithEmail(String email) {
		 
		return empDao.getFullNameWithEmail(email);
	}

	@Override
	public List<String> getRolesWithEmail(String email) {
		 
		return empDao.getRolesWithEmail(email);
	}

	@Override
	public String setPasswordWithEmail(String email,String oldPswrd,String newPswrd) throws passwordNotMatchedException {
		String DbPswrd=empDao.getPasswordWithEmail(email);
		if(passwordEncoder.matches(oldPswrd, DbPswrd)) {
			//encoding the password and Store in db
			return empDao.setPasswordWithEmail(passwordEncoder.encode(newPswrd),email);
		}else {
			throw new  passwordNotMatchedException("Password Not Matched");
		}
	}

	@Override
	public Page<Employee> getAllEmployees(String department, String role, String email, String phone,Pageable pageable) {
		Page<Employee> page=null;
		
		if(!email.isBlank()&&department.isBlank()&&role.isBlank()&&phone.isBlank()) {
			page=empDao.findByEmail(email, pageable);							// only email
		}else if(!phone.isBlank()&&email.isBlank()&&department.isBlank()&&role.isBlank()) {
			page=empDao.findByPhoneNumber(phone, pageable);						//only phone
		}else if(phone.isBlank()&&email.isBlank()&&!department.isBlank()&&role.isBlank()){
			page=empDao.findByDepartment(department, pageable);					//only department
		}else if(phone.isBlank()&&email.isBlank()&&department.isBlank()&&!role.isBlank()) {
			page=empDao.findByRoles(Set.of(role), pageable);					// only role
		}else if(phone.isBlank()&&email.isBlank()&&!department.isBlank()&&!role.isBlank()) {
			page=empDao.findByDepartmentAndRoles(department, Set.of(role), pageable);//only dept and role
		}else if(phone.isBlank()&&!email.isBlank()&&!department.isBlank()&&role.isBlank()) {
			page=empDao.findByDepartmentAndEmail(department, email, pageable);       // email and dept
		}else if(!phone.isBlank()&&email.isBlank()&&!department.isBlank()&&role.isBlank()) {
			page=empDao.findByDepartmentAndPhoneNumber(department, phone, pageable);  // dept and phone
		} else if(phone.isBlank()&&!email.isBlank()&&department.isBlank()&&!role.isBlank()) {
			page=empDao.findByRolesAndEmail(Set.of(role), email, pageable);			  //role and email
		}else if(!phone.isBlank()&&email.isBlank()&&department.isBlank()&&!role.isBlank()) {
			page=empDao.findByRolesAndPhoneNumber(Set.of(role), phone, pageable);	  //role and phone
		}else if(phone.isBlank()&&!email.isBlank()&&!department.isBlank()&&!role.isBlank()) {
			page=empDao.findByDepartmentAndRolesAndEmail(department, Set.of(role), email, pageable); //dept, role and email
		}else if(!phone.isBlank()&&email.isBlank()&&!department.isBlank()&&!role.isBlank()) {
			page=empDao.findByDepartmentAndRolesAndPhoneNumber(department, Set.of(role), phone, pageable); //dept, role and phone
		}else{
			page=empDao.getAllEmployees(pageable); // All emp
		}
		return page;
	}

	@Override
	public boolean isEmployeeExist(String email) {
		 
		return empDao.isEmployeeExist(email);
	}

	@Override
	public String deleteEmployeeReq(String email,String requestedBy,String reasonForDeletion) throws EmployeeNotFoundException {
		EmployeeDeletion empDlte=new EmployeeDeletion();
		empDlte.setName(empDao.getFullNameWithEmail(email));
		empDlte.setDepartment(empDao.getEmployeeDepartmentbyEmail(email));
		empDlte.setEmail(email);
		empDlte.setRequestedBy(requestedBy);
		empDlte.setRequestedDate(new Date());
		empDlte.setStatus("Requested");
		empDlte.setReasonForDeletion(reasonForDeletion);
		empDlte.setJoinedDate(empDao.getEmployeeDateOfJoiningbyEmail(email));
		return empDao.addEmployeeDeletion(empDlte);
	}

	@Override
	public List<String> getEmailsBasedOnRole(Set<String> roles) {
		return empDao.getEmailsBasedOnRole(roles);
	}

	@Override
	public List<Employee> findEmployeesByRoles(Set<String> roles) {
 		return empDao.findEmployeesByRoles(roles);
	}

	@Override
	public List<String> findByDepartment(String department) {
 		return empDao.findByDepartment(department);
	}

	@Override
	public void setregisteredTeamWithEmail(String registeredTeam, String email) {
		empDao.setregisteredTeamWithEmail(registeredTeam, email);
	}

	@Override
	public void setRemoveRegisteredTeamWithEmail(String email) {
		empDao.setRemoveRegisteredTeamWithEmail(email);
	}

	@Override
	public String ForgotPasswordSendOTP(String email) throws MessagingException {
		String OTP=new Random().nextInt(1000, 9999)+"";
		System.out.println(OTP);
//		mailSender.sendForgotPasswordMail(email, OTP);
		return OTP;
	}

	@Override
	public String updateForgotPassword(String email, String password) throws EmployeeNotFoundException {
 		return empDao.setPasswordWithEmail(passwordEncoder.encode(password), email);
	}

	@Override
	public String addOTP(OTPTable otpTable) {
 		return empDao.addOTP(otpTable);
	}

	@Override
	public OTPTable getOTP(String email) throws EmployeeNotFoundException {
 		return empDao.getOTP(email);
	}

	@Override
	public String deleteOTP(String email) {
 		return empDao.deleteOTP(email);
	}

	@Override
	public boolean isEmployeeOldWorker(String email) {
		return empDao.isEmployeeOldWorker(email);
	}

	@Override
	public Optional<Employee> getOldEmployeedetais(String email) {
 		return empDao.getOldEmployeedetais(email);
	}

	@Override
	public int changeRole(Set<String> roles, String email) throws EmployeeNotFoundException {
 		return empDao.changeRole(roles, email);
	}

	@Override
	public int changeDepartment(String department, String email) throws EmployeeNotFoundException {
 		return empDao.changeDepartment(department, email);
	}

	@Override
	public List<EmployeeDeletion> getEmployeeDeletion(String email) {
 		return empDao.getEmployeeDeletion(email);
	}

	@Override
	public String addEmployeeDeletion(EmployeeDeletion deletion) {
 		return empDao.addEmployeeDeletion(deletion);
	}

	@Override
	public int upadteEmployeeDeletionStatusbyEmail(String status, Long employeeDeletionId) {
 		return empDao.upadteEmployeeDeletionStatusbyEmail(status, employeeDeletionId);
	}

	@Override
	public int upadteEmployeeDeletionRequestedBybyEmail(Date requestedDate, String requestedBy,
			Long employeeDeletionId) {
 		return empDao.upadteEmployeeDeletionRequestedBybyEmail(requestedDate, requestedBy, employeeDeletionId);
	}

	@Override
	public int upadteEmployeeDeletionApprovedBybyEmail(Date approvedDate, String requestedBy, Long employeeDeletionId) {
 		return empDao.upadteEmployeeDeletionApprovedBybyEmail(approvedDate, requestedBy, employeeDeletionId);
	}

	@Override
	public Page<EmployeeDeletion> getEmployeeDeletionPage(String email, Long employeeDeletionId, String department,
			String status, Pageable pg) {
		Page<EmployeeDeletion> page;
		
		if(employeeDeletionId!=null&&employeeDeletionId!=0) {
			page=empDao.findByEmployeeDeletionId(employeeDeletionId, pg);
		}else if(email!=null&&!email.isBlank()) {
			page=empDao.findDeletionEmpByEmail(email, pg);
		}else if(status!=null&&!status.isBlank()) {
			if(department!=null&&!department.isBlank()) {
				page=empDao.findDeletionEmpByDepartmentAndStatus(department, status, pg);
			}else {
				page=empDao.findDeletionEmpByStatus(status, pg);
			}
		}else if(department!=null&&!department.isBlank()) {
			page=empDao.findDeletionEmpByDepartment(department, pg);
		}else {
			page=empDao.findAllDeletionEmp(pg);
		}		
 		return page;
	}

	@Override
	public EmployeeDeletion getEmployeeDeletionbyId(Long id) throws EmployeeNotFoundException {
 		return empDao.getEmployeeDeletionbyId(id);
	}

	@Override
	public String deleteEmployee(String email) throws EmployeeNotFoundException {
		String department=empDao.getEmployeeDepartmentbyEmail(email);
		List<String> roles=empDao.getRolesWithEmail(email);
		switch(department){
			case "Production":
				messageProducer.MessageForProductionDeleteEmployee(email);
				if(roles.contains("PRODUCTION_SUPERVISOR"))
					empDao.setRemoveRegisteredTeamwithDepartmentAndRegisteredTeam(department, email);
				break;
			case "Inventory":
				messageProducer.MessageForInventoryDeleteEmployee(email);
				break;
			case "Maintenance":
				messageProducer.MessageForMaintenanceDeleteEmployee(email);
				if(roles.contains("MAINTENANCE_SUPERVISOR"))
					empDao.setRemoveRegisteredTeamwithDepartmentAndRegisteredTeam(department, email);
				break;
		}
 		return empDao.deleteEmployee(email);
	}

	@Override
	public int updateEmpReJoinedToTrue(String email) {
 		return empDao.updateEmpReJoinedToTrue(email);
	}

	@Override
	public Date getEmployeeDateOfJoiningbyEmail(String email) {
 		return empDao.getEmployeeDateOfJoiningbyEmail(email);
	}
	

}









