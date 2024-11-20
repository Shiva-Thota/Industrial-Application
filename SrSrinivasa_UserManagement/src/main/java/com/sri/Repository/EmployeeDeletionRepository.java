package com.sri.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sri.Entity.EmployeeDeletion;

import jakarta.transaction.Transactional;


public interface EmployeeDeletionRepository extends JpaRepository<EmployeeDeletion, Long> {

	
	Page<EmployeeDeletion> findByEmail(String email,Pageable pg);
	Page<EmployeeDeletion> findByDepartment(String department,Pageable pg);
	Page<EmployeeDeletion> findByStatus(String status,Pageable pg);
	
	Page<EmployeeDeletion> findByDepartmentAndStatus(String department, String status,Pageable pg);
	Page<EmployeeDeletion> findByEmployeeDeletionId(Long employeeDeletionId,Pageable pg);
	
	
	List<EmployeeDeletion> findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("update EmployeeDeletion set status =:status where employeeDeletionId =:employeeDeletionId")
	int upadteEmployeeDeletionStatusbyEmail(String status,Long employeeDeletionId);
	
	@Transactional
	@Modifying
	@Query("update EmployeeDeletion set requestedBy =:requestedBy, requestedDate =:requestedDate where employeeDeletionId =:employeeDeletionId")
	int upadteEmployeeDeletionRequestedBybyEmail(Date requestedDate,String requestedBy,Long employeeDeletionId);
	
	@Transactional
	@Modifying
	@Query("update EmployeeDeletion set approvedBy =:approvedBy,approvedDate =:approvedDate where employeeDeletionId =:employeeDeletionId")
	int upadteEmployeeDeletionApprovedBybyEmail(Date approvedDate,String approvedBy,Long employeeDeletionId);
	
	
	
	
}
