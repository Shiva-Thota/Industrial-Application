package com.sri.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sri.Service.EmployeeService;

@Controller
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
	
}
