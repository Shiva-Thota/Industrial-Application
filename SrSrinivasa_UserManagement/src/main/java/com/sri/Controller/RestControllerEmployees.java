package com.sri.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sri.DTO.Prod_User_DTO_Employee;
import com.sri.Entity.Employee;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Service.EmployeeService;
import com.sri.utils.EmployeeUtils;

@RestController
@RequestMapping("/employee")
public class RestControllerEmployees {
	
	@Autowired
	EmployeeService empSer;
	
	@Value("${modelAttribute.Roles}")
	List<String> Roles;
	
	@GetMapping("/empEmailListByRole")
	public ResponseEntity<?> getEmployeesBasedonRole(@RequestParam("role") String role,@RequestParam("reqUsername") String reqUsername){
	 
 		try {
 			Prod_User_DTO_Employee reqUser=EmployeeUtils.EmployeeToProd_User_DTO(empSer.getEmployee(reqUsername));
			String reqUsrDprt=reqUser.getDepartment();
			List<String> emails=empSer.getEmailsBasedOnRole(Set.of(role));
			List<Prod_User_DTO_Employee> empList=new ArrayList<Prod_User_DTO_Employee>();
 			emails.forEach(email->{
				try {
					Prod_User_DTO_Employee dto=EmployeeUtils.EmployeeToProd_User_DTO(empSer.getEmployee(email));
					dto.setPhoto(null);
					if(reqUsrDprt.equalsIgnoreCase(dto.getDepartment())||reqUser.getRoles().contains("GENERAL_MANAGER"))
									empList.add(dto);
				} catch (EmployeeNotFoundException e) {
					e.printStackTrace();
				}
			});
 			return ResponseEntity.ok(empList);
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	@GetMapping("/empRolesList")
	public ResponseEntity<List<String>> getEmployeesAllRolesList(){
		return ResponseEntity.ok(getRoles());
	}
	
	@GetMapping("/empMailandSpecializationList/{role}")
	public ResponseEntity<?> getEmployeemailandCapabilities(@PathVariable("role") String role){
		try {
			List<Employee> employees=empSer.findEmployeesByRoles(Set.of(role));
			Map<String,String> mailAndSpecializtions=new HashMap<String,String>();
			employees.forEach(emp->mailAndSpecializtions.put(emp.getEmail(), emp.getCapabilities()));
			return ResponseEntity.ok(mailAndSpecializtions);
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/getEmpByEmail/{email}")
	public ResponseEntity<?> getEmployeeBasedOnEmail(@PathVariable("email") String email){
		try {
			Prod_User_DTO_Employee employee=EmployeeUtils.EmployeeToProd_User_DTO(empSer.getEmployee(email));
			return ResponseEntity.ok(List.of(employee));
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		            .body("Employee with ID " + email + " not found.");
		}
	}
	
	@PostMapping("/getEmpListbyEmailList")
	public ResponseEntity<?> getEmpListbyEmailList(@RequestParam("emailList") List<String> emailList){
		try {
			List<Prod_User_DTO_Employee> empList=new ArrayList<Prod_User_DTO_Employee>();
			emailList.forEach(email->{
				try {
					Prod_User_DTO_Employee dto=EmployeeUtils.EmployeeToProd_User_DTO(empSer.getEmployee(email));
					dto.setPhoto(null);
					empList.add(dto);
				} catch (EmployeeNotFoundException e) {
					e.printStackTrace();
				}
			});
			return ResponseEntity.ok(empList);
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	@GetMapping("/isEmployeeExist/{email}")
	public ResponseEntity<?> isEmployeeExists(@PathVariable("email") String email){
		try{
			boolean exist=empSer.isEmployeeExist(email);
			if(exist)
				return ResponseEntity.ok("Exist");
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
			            .body("Employee with ID " + email + " not found.");
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/updateEmpRegisteredTeam/{registeredTeam}/{email}")
	public ResponseEntity<?> updateEmployeeRegisteredTeam(@PathVariable("registeredTeam") String registeredTeam,@PathVariable("email") String email){
		try {
			empSer.setregisteredTeamWithEmail(registeredTeam, email);
			return ResponseEntity.ok(HttpStatus.OK);
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("/updateRemoveEmpRegisteredTeam/{email}")
	public ResponseEntity<?> updateRemoveEmployeeRegisteredTeam(@PathVariable("email") String email){
		try {
			empSer.setRemoveRegisteredTeamWithEmail(email);
			return ResponseEntity.ok(HttpStatus.OK);
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ModelAttribute("Roles")
	public List<String> getRoles(){		
		return Roles;
	}
	
}





