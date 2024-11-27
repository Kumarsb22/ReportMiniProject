package com.secondminiproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondminiproject.entities.EnquiryStatus;

public interface EnqueryStatusRepository extends JpaRepository<EnquiryStatus, Integer> {

	
//	public List<String> findEnqStatus();

}
