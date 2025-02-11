package com.sri.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class DashboardHandler {
	
	@GetMapping({"/","/Dashboard/","/Dashboard/hr"})
	public String getHr() {
		return "HREmployeeDashboard";
	}	
	
	@GetMapping("/error-403")
	public String getErrorPage() {
		return "error-403";
	}
}
