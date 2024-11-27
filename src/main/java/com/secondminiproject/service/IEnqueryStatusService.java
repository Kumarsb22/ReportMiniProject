package com.secondminiproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secondminiproject.bindingclasses.EnquirySearchCriteria;
import com.secondminiproject.entities.EnquiryStatus;

public interface IEnqueryStatusService {

	public List<String> getAllEnqueryStatus();


}
