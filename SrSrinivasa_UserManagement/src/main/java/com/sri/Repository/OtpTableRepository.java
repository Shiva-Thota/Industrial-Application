package com.sri.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.Entity.OTPTable;

public interface OtpTableRepository extends JpaRepository<OTPTable, String> {

}
