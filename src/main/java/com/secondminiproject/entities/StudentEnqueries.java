package com.secondminiproject.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "STUDENT_ENQUERY_DETAILS")
public class StudentEnqueries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqueryId;
	private String studentName;
	private String phone;
	private String classMode;
	private String courseName;
	private String enqueryStatus;
	private LocalDate createdDate;
	private LocalDate updatedDate;
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;

}
