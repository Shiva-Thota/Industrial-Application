package com.sri.Runner;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sri.Entity.Employee;
import com.sri.Persistance.EmployeeDAO;
import com.sri.Repository.EmployeeRepository;
@Component
public class EmployeeTester implements ApplicationRunner {
	
	@Autowired
	EmployeeRepository repo;
	
	@Autowired
	EmployeeDAO dao;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Sri Srinivasa Industries");
		
//		Employee e=new Employee();
//		e.setEmail("shiv147417a@gmail.com");
//		e.setAddharNo("8830 2156 1310");
//		e.setAddress("Deekshakunta");
//		e.setDateOfBirth(new Date());
//		e.setDepartment("Maintainanace");
//		e.setEnabled(true);
//		e.setFather("Yakaiah");
//		e.setGender("Male");
//		e.setLastName("Thota");
//		e.setFirstName("Shiva");
//		Set<String> role=new HashSet<String>();
//		role.add("MANAGER");
//		e.setRoles(role);
//		e.setPhoneNumber("6302068819");
//		//e.setPhoto(new byte[45]);
//		String msg=ser.addEmployee(e);
////		System.out.println(msg);
//		System.out.println("\n");
//		Optional<Employee> isEmployeeDeleted = repo.IsEmployeeDeleted("thotashiva1@gmail.com");
//		if(isEmployeeDeleted.isEmpty()) {
//			System.out.println("noooooooooooooooooooooooo");
//		}else {
//			System.out.println(isEmployeeDeleted.get());
//			System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
//		}
//		System.out.println("\n\n\n"); 
//		System.out.println(repo.enablingDeletedEmployeeToTrue("thotashiva1@gmail.com"));
//		Employee employeeByEnabledId =dao.getEmployee("thotashiva1@gmail.com");
//		System.out.println(employeeByEnabledId);
//		System.out.println("\n\n\n");
//
//	} 
	}
}
  