package com.secondminiproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secondminiproject.entities.EnquiryStatus;
import com.secondminiproject.repositories.EnqueryStatusRepository;

@Service
public class EnqueryStatusServiceImpl implements IEnqueryStatusService {

	@Autowired
	private EnqueryStatusRepository enqueryStatusRepository;

	@Override
	public List<String> getAllEnqueryStatus() {
		List<EnquiryStatus> findAll = this.enqueryStatusRepository.findAll();

		ArrayList<String> status = new ArrayList<>();

		for (EnquiryStatus enquiryStatus : findAll) {
			status.add(enquiryStatus.getEnqueryStatusName());
		}
		return status;
	}

}
