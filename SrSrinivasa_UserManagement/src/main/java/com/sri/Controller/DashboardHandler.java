package com.sri.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.Service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class DashboardHandler {

	@Autowired
	EmployeeService empSer;
	
	@Value("${gateway.url}")
	String gateWayUrl;
	
	@GetMapping("/")
	public String getDashboard() {
  		return "Home";
	}
	
	@GetMapping({"/Dashboard/","/Dashboard/hr"})
	public String getHr() {
		return "HREmployeeDashboard";
	}
	
	 
}
