package com.sri.Controller;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sri.Entity.AuthRequest;
import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Exceptions.NotAuthorizedException;
import com.sri.Exceptions.passwordNotMatchedException;
import com.sri.Service.EmployeeService;
import com.sri.Validators.EmployeeRegistrationValidator;
import com.sri.Validators.EmployeeUpdateFormValidator;

@Controller
@RequestMapping("/user")
public class UserHandler {
	
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
	
	@GetMapping("/loginPage")
	public String getLoginPage() {
		return "EmployeeLogin";
	}
	
	//Profile Page
	@GetMapping("/profile")
	public String getProfilePage(@ModelAttribute Employee emp,Map<String,Object> map,Principal principal) {
		String email=principal.getName();
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
	
	
	@GetMapping("/updateEmp")
	public String getUpdatePage(@ModelAttribute EmployeeModel employeeModel,Map<String,Object> map,Principal principal) {
		String email=principal.getName();
		try {
			EmployeeModel empModel=empSer.getEmployeeModel(email);
			map.put("employeeModel", empModel);
		}catch (EmployeeNotFoundException e) {
			map.put("EmpNotFound", "Employee Not Found in database");
		}
		return "EmployeeUpdate";
	}
	
	@GetMapping("/passwordChangePage")
	public String getPasswordChangePage() {
		return "EmployeePswrdChnge";
	}
	
	@GetMapping("/changePassword")
	public String changePassword(@RequestParam("oldPswrd") String oldPswrd,@RequestParam("newPswrd") String newPswrd,Principal principal,Map<String,Object> map) {
		try {
			empSer.setPasswordWithEmail(principal.getName(), oldPswrd, newPswrd);
			map.put("msg", "Password Updated");
		} catch (passwordNotMatchedException e) {

			map.put("msg", "Password Not Matched");
			return "EmployeePswrdChnge";
		}
		return "EmployeePswrdChnge";
	}
	
	 
	
	@PostMapping("/updateEmp")
	public String updateEmployeee(@ModelAttribute EmployeeModel employeeModel,RedirectAttributes redirectAttribute,BindingResult error,Principal principal) throws NotAuthorizedException {
		String email=principal.getName();
		if(!employeeModel.getEmail().equals(email))
			throw new NotAuthorizedException("Not Authorized");
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
	
	@ModelAttribute("Departments")
	public List<String> getDepartments(){		
		return Departments;
	}
	
	@ModelAttribute("Roles")
	public List<String> getRoles(){		
		return Roles;
	}
	
}
