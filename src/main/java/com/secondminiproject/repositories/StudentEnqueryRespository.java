package com.secondminiproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondminiproject.entities.StudentEnqueries;

public interface StudentEnqueryRespository extends JpaRepository<StudentEnqueries, Integer> {

}
