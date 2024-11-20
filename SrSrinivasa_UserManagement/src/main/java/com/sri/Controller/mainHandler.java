package com.sri.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.DTO.AuthRequest;
import com.sri.Service.EmployeeService;

@Controller
//@RequestMapping("/employee")
public class mainHandler {
	
	@Autowired
	EmployeeService empSer;

	@GetMapping("/")
	public String getHomePage() {
		return "Home";
	}
	@GetMapping("/accessDenined403")
	public String getaccessDenined403Page() {
		return "error-403";
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "loginPage";
	}
	
	@GetMapping("/error-403")
	public String getErrorPage() {
		return "error-403";
	}
	
}
