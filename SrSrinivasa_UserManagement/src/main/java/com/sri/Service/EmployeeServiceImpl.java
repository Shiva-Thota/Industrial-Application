package com.sri.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.passwordNotMatchedException;
import com.sri.Persistance.EmployeeDAO;
import com.sri.mail.MailSender;
import com.sri.utils.EmployeeUtils;

import jakarta.mail.MessagingException;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeDAO empDao;
	
	@Autowired
	MailSender mailSender;
	
	@Value("${Password_Mail_Text}")
	String passwordMailText;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public String addEmployee(Employee employee) throws SQLException, MessagingException {
		Employee emp=employee;
		
		//setting todays date
		emp.setDateOfJoining(new Date());		
		
		//setting the random password
		char[] ch=new char[10];
		for(int i=0;i<10;i++) {
			ch[i]=(char)new Random().nextInt(65,123);
		}
		String password=String.valueOf(ch);
		emp.setPassword(passwordEncoder.encode(password));
		
		
		//sending password to the mail
		mailSender.sendJoiningMail(emp.getFirstName()+" "+emp.getLastName(),emp.getEmail(),emp.getRoles(),password);
		
		return empDao.addEmployee(emp);
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
	public String deleteEmployee(String email) throws EmployeeNotFoundException {
		 
		return empDao.deleteEmployee(email);
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
		mailSender.sendForgotPasswordMail(email, OTP);
		return OTP;
	}

	@Override
	public String updateForgotPassword(String email, String password) throws EmployeeNotFoundException {
 		return empDao.setPasswordWithEmail(password, email);
	}

	

}
