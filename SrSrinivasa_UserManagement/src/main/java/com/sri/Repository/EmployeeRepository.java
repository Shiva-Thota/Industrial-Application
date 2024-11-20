package com.sri.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sri.Entity.Employee;

import jakarta.transaction.Transactional;


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
	@Query("update Employee Set department =:department where email =:email")
	int updateEmpDepartment(String department,String email);
	
	@Modifying 
	@Transactional
	@Query("update Employee Set reJoined =:reJoined where email =:email")
	int updateEmpReJoinedToTrue(boolean reJoined,String email);
	
	@Modifying
    @Transactional
	@Query("update Employee set password =:pswrd where email =:email AND enabled = true")
    void setPasswordWithEmail(String pswrd,String email);
	
	@Modifying
	@Transactional
	@Query("update Employee set registeredTeam =:registeredTeam where email =:email AND enabled = true")
	void setregisteredTeamWithEmail(String registeredTeam,String email);
	
	@Modifying
	@Transactional
	@Query("update Employee set registeredTeam = null where email =:email AND enabled = true")
	void setRemoveRegisteredTeamWithEmail(String email);
	
	@Modifying 
	@Transactional
	@Query("update Employee set registeredTeam = null where department =:department and registeredTeam =:registeredTeam")
	void setRemoveRegisteredTeamwithDepartmentAndRegisteredTeam(String department,String registeredTeam);
	
	@Query("Select roles from Employee where email =:email")
	Set<String> getEmpRolesByEmail(String email);
	
	
	@Query("SELECT email FROM Employee WHERE department = :department AND enabled = true")
	List<String> findEmailByDepartment(String department);

	@Query("SELECT roles FROM Employee WHERE email = :email AND enabled = true")
	List<String> getRolesWithEmail(String email);

	@Query("SELECT photo FROM Employee WHERE email = :email")
	byte[] getPhotoWithEmail(String email);

	@Query("SELECT CONCAT(firstName, ' ', lastName) AS name FROM Employee WHERE email = :email AND enabled = true")
	String getFullNameWithEmail(String email);
	
	@Query("select department from Employee where email =:email AND enabled = true")
	String getEmployeeDepartmentbyEmail(String email);
	
	@Query("select dateOfJoining from Employee where email =:email AND enabled = true")
	Date getEmployeeDateOfJoiningbyEmail(String email);
	
	@Query("SELECT password FROM Employee WHERE email = :email AND enabled = true")
	String getPasswordWithEmail(String email);

	@Query("SELECT enabled FROM Employee WHERE email = :email")
	boolean isEmployeeEnabled(String email);
	
	@Modifying
	@Transactional
	@Query("update Employee set enabled =:enabled where email =:email")
	int updateEmployeeEnabledToTrue(boolean enabled,String email);

	@Query("from Employee where email =:email And enabled =true")
	Optional<Employee> findByEmailId(String email);
	
	@Query("from Employee where email =:email")
	Optional<Employee> getOldEmployeedetais(String email);

	
	Page<Employee> findByEnabled(boolean enabled,Pageable pg);
	
	Page<Employee>  findByDepartmentAndEnabled(String department, boolean enabled,Pageable pageable);

	Page<Employee> findByPhoneNumberAndEnabled(String phoneNumber,boolean enabled, Pageable pageable);

	Page<Employee> findByEmailAndEnabled(String email,boolean enabled, Pageable pageable);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND e.enabled = true")
	Page<Employee> findByRoles(Set<String> roles, Pageable pageable);

	@Query("SELECT email FROM Employee e JOIN e.roles r WHERE r IN :roles AND e.enabled = true")
	List<String> getEmailsBasedOnRole(Set<String> roles);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND e.enabled = true")
	List<Employee> findEmployeesByRoles(Set<String> roles);

	Page<Employee> findByAddharNoAndEnabled(String addharNo, boolean enabled,Pageable pageable);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND department = :department AND e.enabled = true")
	Page<Employee> findByDepartmentAndRoles(String department, Set<String> roles, Pageable pageable);

	Page<Employee> findByDepartmentAndEmailAndEnabled(String department, String email,boolean enabled, Pageable pageable);

	Page<Employee> findByDepartmentAndPhoneNumberAndEnabled(String department, String phoneNumber,boolean enabled, Pageable pageable);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND email = :email AND e.enabled = true")
	Page<Employee> findByRolesAndEmail(Set<String> roles, String email, Pageable pageable);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND phoneNumber = :phoneNumber AND e.enabled = true")
	Page<Employee> findByRolesAndPhoneNumber(Set<String> roles, String phoneNumber, Pageable pageable);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND department = :department AND email = :email AND e.enabled = true")
	Page<Employee> findByDepartmentAndRolesAndEmail(String department, Set<String> roles, String email, Pageable pageable);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r IN :roles AND phoneNumber = :phoneNumber AND department = :department AND e.enabled = true")
	Page<Employee> findByDepartmentAndRolesAndPhoneNumber(String department, Set<String> roles, String phoneNumber, Pageable pageable);
	
	
}
