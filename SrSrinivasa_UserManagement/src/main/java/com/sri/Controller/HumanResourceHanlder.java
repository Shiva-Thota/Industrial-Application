package com.sri.Controller;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Service.EmployeeService;
import com.sri.Validators.EmployeeRegistrationValidator;
import com.sri.Validators.EmployeeUpdateFormValidator;

@Controller
@RequestMapping("/hr")
public class HumanResourceHanlder {
	
	@Autowired
	EmployeeService empSer;
	
	@Autowired
	EmployeeRegistrationValidator employeeRegistrationValidator;
	
	@Autowired 
	EmployeeUpdateFormValidator employeeUpdateFormValidator;

	@Value("${modelAttribute.Departments}")
	List<String> Departments;
	
	@Value("${modelAttribute.Roles}")
	List<String> Roles;

	
	@GetMapping("/empDashboard")
	public String getEmployeeDashboard() {
		return "HREmployeeDashboard";
	}
	
	
	//Registration Page
	@GetMapping("/register")
	public String getregisterPage(@ModelAttribute Employee employee) {
		return "EmployeeRegister";
	}
	
	//Registering Employee in Database
	@PostMapping("/register")
	public String registerEmployee(@ModelAttribute Employee employee,RedirectAttributes redirectAttribute,BindingResult error ) {
		
		if(employeeRegistrationValidator.supports(Employee.class)) {
			employeeRegistrationValidator.validate(employee, error);
			if(error.hasErrors())
				return "EmployeeRegister";
		}
		String msg=empSer.addEmployee(employee);
		redirectAttribute.addFlashAttribute("Employee Added", msg);
		return "redirect:/";
	}
	
	
	//Getting Update Page 
	@GetMapping("/updateEmp")
	public String getUpdatePage(@RequestParam String email, @ModelAttribute EmployeeModel employeeModel,Map<String,Object> map) {
		try {
			EmployeeModel empModel=empSer.getEmployeeModel(email);
			map.put("employeeModel", empModel);
		}catch (EmployeeNotFoundException e) {
			map.put("EmpNotFound", "Employee Not Found in database");
		}
		return "EmployeeUpdate";
	}
	
	//Updating the details in database
	@PostMapping("/updateEmp")
	public String updateEmployeee(@ModelAttribute EmployeeModel employeeModel,RedirectAttributes redirectAttribute,BindingResult error){
		try {
			if(employeeUpdateFormValidator.supports(EmployeeModel.class)) {
				employeeUpdateFormValidator.validate(employeeModel, error);
				if(error.hasErrors()) {
					System.out.println(error.getAllErrors());
					return "EmployeeUpdate";
				}
			}
			String msg=empSer.updateEmployee(employeeModel);
			redirectAttribute.addFlashAttribute("Employee Updated", msg);
		} catch (EmployeeNotFoundException e) {
			redirectAttribute.addFlashAttribute("EmpNotFound", "Employee Not Found in database");
		}
		return "redirect:/Dashboard/";
	}
	
	//Profile Page
		@GetMapping("/profile")
		public String getProfilePage(@RequestParam String email, @ModelAttribute Employee emp,Map<String,Object> map,Principal principal) {
			String photo="";
			if(empSer.getPhotoWithEmail(email)!=null)
				photo=Base64.getEncoder().encodeToString(empSer.getPhotoWithEmail(email));
			try {
				 emp=empSer.getEmployee(email);
				 emp.setPhoto(null);
				 emp.setAddharCard(null);
			} catch (EmployeeNotFoundException e) {
				e.printStackTrace();
			}
			map.put("EmployeeProfile",emp);
			map.put("EmployeePhoto",photo);
			
			return "EmployeeProfile";
		}
	
	//Getting the List of Employees
	@GetMapping("/EmployeeList")
	public String getallEmployeesList(
			@RequestParam(value="department",required = false) String department,
			@RequestParam(value ="role",required = false ) String role,
			@RequestParam(value ="email",required = false ) String email,
			@RequestParam(value ="phone",required = false ) String phone,
			@PageableDefault(page = 0,size = 1) Pageable pageable,Map<String,Object> map) {
		if(department==null)
			department="";
		if(role==null)
			role="";
		if(email==null)
			email="";
		if(phone==null)
			phone="";
		Page<Employee> page=empSer.getAllEmployees(department,role,email,phone,pageable);
		map.put("EmployeesList", page);
		map.put("selectedDepartment", department);
		map.put("selectedRole", role);
		map.put("selectedEmail", email);
		map.put("selectedPhone", phone);
		
		System.out.println("--------------------------------------------------------------   "+department==null);
		return "EmployeeList";
	}
	
	@ModelAttribute("Departments")
	public List<String> getDepartments(){		
		return Departments;
	}
	
	@ModelAttribute("Roles")
	public List<String> getRoles(){		
		return Roles;
	}
	
}
