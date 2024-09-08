package com.sri.Service;

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
import org.springframework.stereotype.Component;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.passwordNotMatchedException;
import com.sri.Persistance.EmployeeDAO;
import com.sri.mail.MailSender;
import com.sri.utils.EmployeeUtils;

@Component
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
	public String addEmployee(Employee employee) {
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
		mailSender.sendmail(emp.getEmail(), passwordMailText+password);
		
		return empDao.addEmployee(emp);
	}

	@Override
	public Employee getEmployee(String email) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return empDao.getEmployee(email);
	}

	@Override
	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		return empDao.getAllEmployee();
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
		System.out.println(password+"              "+roles+"         "+username);

		Set<SimpleGrantedAuthority> authorities=new HashSet<SimpleGrantedAuthority>();
		//generating SimpleGranted Authority from roles set
		roles.stream().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role));
		});
		User user=new User(username, password, authorities);
		return user;
	}

	@Override
	public byte[] getPhotoWithEmail(String email) {
		// TODO Auto-generated method stub
		return empDao.getPhotoWithEmail(email);
	}

	@Override
	public String getFullNameWithEmail(String email) {
		// TODO Auto-generated method stub
		return empDao.getFullNameWithEmail(email);
	}

	@Override
	public List<String> getRolesWithEmail(String email) {
		// TODO Auto-generated method stub
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
	public Page<Employee> findByDepartment(String department, Pageable pageable) {
		// TODO Auto-generated method stub
		return empDao.findByDepartment(department, pageable);
	}

	@Override
	public Page<Employee> findByRoles(Set<String> roles, Pageable pageable) {
		// TODO Auto-generated method stub
		return empDao.findByRoles(roles, pageable);
	}

	@Override
	public Page<Employee> getAllEmployees(Pageable pageable) {
		// TODO Auto-generated method stub
		return empDao.getAllEmployees(pageable);
	}

}
