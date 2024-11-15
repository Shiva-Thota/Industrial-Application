package com.sri.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sri.Service.EmployeeService;
import com.sri.utils.JSONWebTokenUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/employee")
public class AuthRestController {
	
	@Autowired
	EmployeeService empSer;
	
	@Autowired
	JSONWebTokenUtil jsonWebTokenUtil;
	
	 @Autowired
	 private AuthenticationManager authenticationManager;

	  
//	  @GetMapping("/loginPage")
//		public String getLoginPage() {
//			return "EmployeeLogin";
//		}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
//		Authentication authentication=authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
//												authRequest.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		List<String> roles=empSer.getRolesWithEmail(authRequest.getUsername());
//		String Name=empSer.getFullNameWithEmail(authRequest.getUsername());
//		String jwt=jsonWebTokenUtil.generateToken(authRequest.getUsername(), Map.of("ROLES",roles,"Name",Name));
//		return ResponseEntity.ok(new AuthResponse(jwt));
//	}
	  
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestParam("username") String username,@RequestParam("password") String password) {
 		try {
			Authentication authentication=authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(username,password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			List<String> roles=empSer.getRolesWithEmail(username);
			String Name=empSer.getFullNameWithEmail(username);
			String jwt=jsonWebTokenUtil.generateToken(username, Map.of("ROLES",roles,"Name",Name));
 	         return  ResponseEntity.ok(jwt);
 		}catch(Exception e) {
 			System.out.println("No Employee Found");
 			return new ResponseEntity<String>("NO Employee Found", HttpStatus.NOT_FOUND);
 		}
         
	}
}
