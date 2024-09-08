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
	public void updateEmployee(byte[] adhrCard,String addrs,String blood,Date dob,String emgCont,
			String emgPer,String father,String fname,String gender,String lname,String marital,String phone,
			byte[] photo,String adhrNo,String email);
	
	@Modifying
    @Transactional
	@Query("update Employee set password =:pswrd where email =:email")
	public void setPasswordWithEmail(String pswrd,String email);
	
	
	
	@Query("Select roles from Employee where email =:email")
	public List<String> getRolesWithEmail(String email);
	
	@Query("Select photo from Employee where email =:email")
	public byte[] getPhotoWithEmail(String email);
			
	@Query("select CONCAT(firstName,' ',lastName) as name from Employee where email =:email")
	public String getFullNameWithEmail(String email);
	
	@Query("Select password from Employee where email =:email")
	public String getPasswordWithEmail(String email);
	
	public Page<Employee> findByDepartment(String department,Pageable pageable);

	public Page<Employee> findByRoles(Set<String> roles,Pageable pageable);
	
}
