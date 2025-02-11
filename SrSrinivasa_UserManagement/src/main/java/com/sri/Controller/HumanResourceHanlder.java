package com.sri.Controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sri.Entity.Employee;
import com.sri.Entity.EmployeeDeletion;
import com.sri.Entity.EmployeeModel;
import com.sri.Exceptions.EmployeeNotFoundException;
import com.sri.Service.EmployeeService;
import com.sri.Validators.EmployeeRegistrationValidator;
import com.sri.Validators.EmployeeUpdateFormValidator;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/employee/hr")
public class HumanResourceHanlder {
	
	@Autowired
	EmployeeService empSer;
	
	@Autowired
	EmployeeRegistrationValidator employeeRegistrationValidator;
	
	@Autowired 
	EmployeeUpdateFormValidator employeeUpdateFormValidator;
	
	@Value("${gateway.url}")
	String gateWayUrl;
	
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
		if(empSer.isEmployeeOldWorker(employee.getEmail())) {
			redirectAttribute.addFlashAttribute("OldEmployee", "OLD Employee");
			return "redirect:"+gateWayUrl+"/employee/hr/enableOldEmp/"+employee.getEmail();
		}
		
		if(employeeRegistrationValidator.supports(Employee.class)) {
			employeeRegistrationValidator.validate(employee, error);
			if(error.hasErrors())
				return "EmployeeRegister";
		}
		String msg="";
		try {
			try {
				msg = empSer.addEmployee(employee);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			redirectAttribute.addFlashAttribute("Employee_email_already_exist","Employee_email_already_exist :"+employee.getEmail());
			return "redirect:"+gateWayUrl+"/employee/hr/register";
		}
		redirectAttribute.addFlashAttribute("Employee_Added", msg);
		return "redirect:"+gateWayUrl+"/employee/hr/empDashboard";
	}
	
	@GetMapping("/enableOldEmp/{email}")
    public String getOldEmpdetails(@PathVariable("email") String email, @ModelAttribute Employee emp,Map<String,Object> map) {
		String photo="";
		if(!empSer.isEmployeeOldWorker(email)) 
			return "error-404";
		
		if(empSer.getPhotoWithEmail(email)!=null)
			photo=Base64.getEncoder().encodeToString(empSer.getPhotoWithEmail(email));
		try {
			Optional<Employee> oldEmployeedetais = empSer.getOldEmployeedetais(email);
			if(oldEmployeedetais.isEmpty())
				throw new EmployeeNotFoundException("");
			emp=oldEmployeedetais.get();
			 emp.setPhoto(null);
			 emp.setAddharCard(null);
		} catch (EmployeeNotFoundException e) {
			 
		}
		map.put("EmployeeProfile",emp);
		map.put("EmployeePhoto",photo);
		return "OldEmpProfile";
	}
	
	@PostMapping("/enableOldEmployee")
	public String enableOlDEmployee(@RequestParam("email") String email) {
		try {
 			empSer.addOldEmployee(email);
			 return "redirect:"+gateWayUrl+"/employee/hr/profile?email="+email;
		} catch (EmployeeNotFoundException e) {
			return "";
 		}
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
					
					return "EmployeeUpdate";
				}
			}
			String msg=empSer.updateEmployee(employeeModel);
			redirectAttribute.addFlashAttribute("Employee Updated", msg);
		} catch (EmployeeNotFoundException e) {
			redirectAttribute.addFlashAttribute("EmpNotFound", "Employee Not Found in database");
		}
		return "redirect:"+gateWayUrl+"/employee/Dashboard/";
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
			 if(emp.isReJoined()) {
				 List<EmployeeDeletion> employeeDeletionList = empSer.getEmployeeDeletion(email);
				 map.put("employeeDeletionList", employeeDeletionList);
			 }
		} catch (EmployeeNotFoundException e) {
			return "error-404";
		}
		
		map.put("employee",emp);
		map.put("EmployeePhoto",photo);
		return "EmployeeDetails";
	}
	
	//Getting the List of Employees
	@GetMapping("/EmployeeList")
	public String getallEmployeesList(
			@RequestParam(value="department",required = false) String department,
			@RequestParam(value ="role",required = false ) String role,
			@RequestParam(value ="email",required = false ) String email,
			@RequestParam(value ="phone",required = false ) String phone,
			@PageableDefault(page = 0,size = 20) Pageable pageable,Map<String,Object> map) {
			
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
		return "EmployeeList";
	}
	@GetMapping("/EmployeeList/download")
	public String downloadEmployeesList(
			@RequestParam(value="department",required = false) String department,
			@RequestParam(value ="role",required = false ) String role,
			@RequestParam(value ="email",required = false ) String email,
			@RequestParam(value ="phone",required = false ) String phone,
			@RequestParam(value ="type",required = false ) String type,
			@PageableDefault(page = 0,size = 50) Pageable pageable,Map<String,Object> map) {
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
		if(type.equalsIgnoreCase("Excel"))
				return "Excel_Download";
		
		return "Pdf_Download";
	}
	
	@GetMapping("/changeRoleandDprt/{email}")
	public String getChangeRoleAndDepartmentPage(@PathVariable("email") String email,Map<String,Object> map) {
		try {
			Employee emp=empSer.getEmployee(email);
			map.put("empName", emp.getFirstName()+" "+emp.getLastName());
			map.put("empDepartment", emp.getDepartment());
			map.put("empRoles", emp.getRoles());
			map.put("empEmail", emp.getEmail());
			return "EmpRoleAndDprtChange";
		} catch (EmployeeNotFoundException e) {
 			return "error-404";
		}
	}
	@PostMapping("/changeRoleandDprt")
	public String changeRoleandDepartment(@RequestParam("email") String empEmail,
			@RequestParam("roles") Set<String> empRoles,@RequestParam("department") String empDepartment) {
		try {
			empSer.changeDepartment(empDepartment, empEmail);
			empSer.changeRole(empRoles, empEmail);
			 return "redirect:"+gateWayUrl+"/employee/hr/profile?email="+empEmail;
		} catch (EmployeeNotFoundException e) {
			return "error-404";
		}
	}
	
	//Delete Emp List
	@PostMapping("/addToDeleteEmp")
	public String delteEmp(@RequestParam("email") String email,@RequestParam("reasonForDeletion") String reasonForDeletion,Map<String,Object> map,Principal principal) {
			String msg="";
 		try {
 			if(!empSer.isEmployeeExist(email))
				throw new EmployeeNotFoundException("");
 			msg= empSer.deleteEmployeeReq(email,principal.getName(),reasonForDeletion);
			map.put("Emp_Deleted","Employee Deletion request Raised");
		} catch (EmployeeNotFoundException e) {
			map.put("Emp_Deleted","Email doesn't Exist");
			return "HREmployeeDashboard";
		}
		return "redirect:"+gateWayUrl+"/employee/hr/empDashboard";
	}	
	
	
	
	@GetMapping("/dlteEmpReqList")
	public String getDeleteEmpReqList(@RequestParam(name="email",required = false) String email,@RequestParam(name="dprtmnt",required = false) String department,@RequestParam(name="sts",required = false) String status,
			@RequestParam(name="empDltnId",required = false) Long employeeDeletionId,@PageableDefault(size =15,page = 0)  Pageable pg,Map<String,Object> map) {
		try {
			
		if(email==null) {
			email="";
		}
		if(department==null) {
			department="";
		}
		if(status==null) {
			status="";
		}
		if(employeeDeletionId==null) {
			employeeDeletionId=(long) 0;
		}	
		map.put("email", email);
		map.put("department",department);
		map.put("status",status);
		map.put("employeeDeletionId",employeeDeletionId);
		
		Page<EmployeeDeletion> empList=empSer.getEmployeeDeletionPage(email, employeeDeletionId, department, status, pg);
		map.put("EmployeeDeletionList", empList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "EmployeeDeletionList";
	}
	
	@GetMapping("/dlteEmpReqDetails/{empDlteId}")
	public String EmployeeDeleteDetailsPage(@PathVariable("empDlteId") Long empDlteId,Map<String,Object> map) {
		try {
			EmployeeDeletion employeeDeletionbyId = empSer.getEmployeeDeletionbyId(empDlteId);
			map.put("EmployeeDeletion", employeeDeletionbyId);
		} catch (EmployeeNotFoundException e) {
			return "error-404";
		}
		return "EmployeDeletionDetails";
	}
	
	@GetMapping("/dlteEmpReq/reject/{id}")
	public String rejectDeleteEmployeeRequest(@PathVariable("id") Long id,Principal principal) {
		empSer.upadteEmployeeDeletionStatusbyEmail("Rejected", id);
		empSer.upadteEmployeeDeletionApprovedBybyEmail(new Date(), principal.getName(), id);
		return "HREmployeeDashboard";
	}
	
	@GetMapping("/dlteEmpReq/approve/{id}/{email}")
	public String approveDeleteEmployeeRequest(@PathVariable("id") Long id,@PathVariable("email") String email,Principal principal) {
		try {
			empSer.deleteEmployee(email);
			empSer.upadteEmployeeDeletionStatusbyEmail("Approved", id);
			empSer.upadteEmployeeDeletionApprovedBybyEmail(new Date(), principal.getName(), id);
			return "HREmployeeDashboard";
		} catch (EmployeeNotFoundException e) {
			return "error-404";
		}
	}
	
	
	@GetMapping("/addBulkEmployee")
	public String addBulkEmployee() {
		return "EmployeeBulkRegister";
	}
	
	@PostMapping("/addBulkEmployee")
	 public String uploadExcelFile(@RequestParam("file") MultipartFile file, Map<String,String> model,RedirectAttributes redirectAttributes) {
		 if (file.isEmpty()) {
	            model.put("message", "Please upload a file!");
	            return "EmployeeBulkRegister";
	        }

	        // Get the file extension
	        String fileExtension = getFileExtension(file.getOriginalFilename());
 	        try {
	            if (fileExtension.equals("xls")) {
	                throw new Exception();
	            } else if (fileExtension.equals("csv")) {
 	                List<Employee> employees = empSer.parseEmployeeCsv(file);
  	                empSer.addAllEmployee(employees);
  	              redirectAttributes.addFlashAttribute("message", "CSV file uploaded and data saved successfully!");
 	            } else {
 	            	redirectAttributes.addFlashAttribute("message", "Please upload a valid .xls or .csv file!");
	            }
  	        } catch (Exception e) {
//	        	e.printStackTrace();
	        	redirectAttributes.addFlashAttribute("message", "Error processing file");
	        }
         return "redirect:"+gateWayUrl+"/employee/hr/addBulkEmployee";
    }
	 // Helper method to get file extension
    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
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
