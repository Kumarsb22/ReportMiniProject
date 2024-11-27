package com.secondminiproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secondminiproject.bindingclasses.DashboardResponse;
import com.secondminiproject.bindingclasses.EnquiryForm;
import com.secondminiproject.bindingclasses.EnquirySearchCriteria;
import com.secondminiproject.entities.StudentEnqueries;

public interface IStudentService {

	public DashboardResponse getDashboardDate(Integer userId);

	public String addEnquiry(EnquiryForm enquiryForm);

	public List<StudentEnqueries> getEnquires(Integer userId, EnquirySearchCriteria criteria);

	public List<StudentEnqueries> getEnquires();

	public StudentEnqueries getEnqById(Integer enqueryId);

}
