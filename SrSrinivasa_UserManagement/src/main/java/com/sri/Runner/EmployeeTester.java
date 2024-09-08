package com.sri.Runner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sri.Entity.Employee;
import com.sri.Service.EmployeeService;

public class EmployeeTester implements ApplicationRunner {
	
	@Autowired
	EmployeeService ser;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Sri Srinivasa Industries");
		
		Employee e=new Employee();
		e.setEmail("shiv147417a@gmail.com");
		e.setAddharNo("8830 2156 1310");
		e.setAddress("Deekshakunta");
		e.setDateOfBirth(new Date());
		e.setDepartment("Maintainanace");
		e.setEnabled(true);
		e.setFather("Yakaiah");
		e.setGender("Male");
		e.setLastName("Thota");
		e.setFirstName("Shiva");
		Set<String> role=new HashSet<String>();
		role.add("MANAGER");
		e.setRoles(role);
		e.setPhoneNumber("6302068819");
		//e.setPhoto(new byte[45]);
		String msg=ser.addEmployee(e);
		System.out.println(msg);
	}

}
  