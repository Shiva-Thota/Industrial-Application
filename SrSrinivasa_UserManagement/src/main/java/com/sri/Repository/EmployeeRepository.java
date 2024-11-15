package com.sri.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sri.Entity.Employee;

import jakarta.transaction.Transactional;
import java.util.Set;


public interface EmployeeRepository extends JpaRepository<Employee, String> {

	@Modifying
    @Transactional
	@Query("update Employee set addharCard =:adhrCard, address =:addrs,bloodGroup =:blood,dateOfBirth =:dob,"
			+ "emergencyContact =:emgCont,emergencyPerson =:emgPer,father =:father,firstName =:fname,gender =:gender,"
			+ "lastName =:lname,maritalStatus =:marital,phoneNumber =:phone,photo =:photo,addharNo =:adhrNo where email =:email")
	  void updateEmployee(byte[] adhrCard,String addrs,String blood,Date dob,String emgCont,
			String emgPer,String father,String fname,String gender,String lname,String marital,String phone,
			byte[] photo,String adhrNo,String email);
	
	@Modifying
    @Transactional
	@Query("update Employee set password =:pswrd where email =:email")
    void setPasswordWithEmail(String pswrd,String email);
	
	@Modifying
	@Transactional
	@Query("update Employee set registeredTeam =:registeredTeam where email =:email")
	void setregisteredTeamWithEmail(String registeredTeam,String email);
	
	@Modifying
	@Transactional
	@Query("update Employee set registeredTeam = null where email =:email")
	void setRemoveRegisteredTeamWithEmail(String email);

	
	@Query("select email from Employee where department =:department")
	List<String> findEmailByDepartment(String department);
	
	@Query("Select roles from Employee where email =:email")
	  List<String> getRolesWithEmail(String email);
	
	@Query("Select photo from Employee where email =:email")
	  byte[] getPhotoWithEmail(String email);
			
	@Query("select CONCAT(firstName,' ',lastName) as name from Employee where email =:email")
	  String getFullNameWithEmail(String email);
	
	@Query("Select password from Employee where email =:email")
	  String getPasswordWithEmail(String email);
	
	@Query("Select enabled from Employee where email =:email")
		boolean isEmployeeEnabled(String email);
	
	  Page<Employee> findByDepartment(String department,Pageable pageable);
	
	  Page<Employee> findByPhoneNumber(String phoneNumber,Pageable pageable);
	  
	  Page<Employee> findByEmail(String email,Pageable pageable);
	
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles")
	  Page<Employee> findByRoles(Set<String> roles,Pageable pageable);
	  
	  @Query("Select email from Employee e JOIN e.roles r where r IN :roles")
	  List<String> getEmailsBasedOnRole(Set<String> roles);
	  
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles")
	  List<Employee> findEmployeesByRoles(Set<String> roles);
	
	
	  Page<Employee> findByAddharNo(String addharNo,Pageable pageable);
	  
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles And department =:department")
	  Page<Employee> findByDepartmentAndRoles(String department, Set<String> roles,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndEmail(String department, String email,Pageable pageable);
	
	  Page<Employee> findByDepartmentAndPhoneNumber(String department, String phoneNumber,Pageable pageable);
	  
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles And email =:email")
	  Page<Employee> findByRolesAndEmail(Set<String> roles, String email,Pageable pageable);
	
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles And phoneNumber =:phoneNumber")
	  Page<Employee> findByRolesAndPhoneNumber(Set<String> roles, String phoneNumber,Pageable pageable);
	
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles And department =:department And email =:email")
	  Page<Employee> findByDepartmentAndRolesAndEmail(String department, Set<String> roles, String email,Pageable pageable);
	
	  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles And phoneNumber =:phoneNumber And department =:department")
	  Page<Employee> findByDepartmentAndRolesAndPhoneNumber(String department, Set<String> roles, String phoneNumber,Pageable pageable);

	  
}
