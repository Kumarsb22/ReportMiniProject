package com.secondminiproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ENQUIRY_STATUS_DETAILS")
public class EnquiryStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqueryStatusId;
	private String enqueryStatusName;

}
