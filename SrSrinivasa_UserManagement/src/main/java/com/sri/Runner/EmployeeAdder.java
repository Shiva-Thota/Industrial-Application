package com.sri.Runner;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sri.Entity.Employee;
import com.sri.Persistance.EmployeeDAO;

@Component
public class EmployeeAdder implements ApplicationRunner{
	
	@Autowired
	EmployeeDAO employeeDAO;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("runner started-------------------");
		try {
			Employee emp=new Employee();
			emp.setEmail("deletehremail@gmail.com");
			emp.setAddharNo("55");
			emp.setBloodGroup("B+");
			emp.setCapabilities("sss");
			emp.setDateOfBirth(new Date());
			emp.setDateOfJoining(new Date());
			emp.setDepartment("Management");
			emp.setEnabled(true);
			emp.setFirstName("delete");
			emp.setLastName("email");
			emp.setPassword(passwordEncoder.encode("admin"));
			emp.setRoles(Set.of("HUMAN_RESOURCE"));
			employeeDAO.addEmployee(emp);
			System.out.println("HR DELETE EMAIL INSERTED ");
			System.out.println("Email : deletehremail@gmail.com     PASSWORD : admin");
		}catch(Exception e) {	
		}
		System.out.println("Runner ended-----------------------");
	}	
	
}
