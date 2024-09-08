package com.sri.Controller;

import java.security.Principal;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sri.Entity.Employee;
import com.sri.Service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Dashboard")
public class DashboardHandler {

	@Autowired
	EmployeeService empSer;
	
	@GetMapping("/")
	public String getDashboard(@ModelAttribute Employee emp,HttpSession session,Principal principal) {
		String photo="";
		String loggedInUser=principal.getName();
		if(empSer.getPhotoWithEmail(loggedInUser)!=null)
			photo=Base64.getEncoder().encodeToString(empSer.getPhotoWithEmail(loggedInUser));
		String userName=empSer.getFullNameWithEmail(loggedInUser);
		List<String> roles=empSer.getRolesWithEmail(loggedInUser);
		session.setAttribute("employeePhoto",photo);
		session.setAttribute("employeeUserName",userName);
		session.setAttribute("employeeRoles",roles);
		session.setAttribute("employeeEmail",loggedInUser);
		
		return "Dashboard";
	}
	
	@GetMapping("/hr")
	public String getHr() {
		return "HRDashboard";
	}
	
	 
}
