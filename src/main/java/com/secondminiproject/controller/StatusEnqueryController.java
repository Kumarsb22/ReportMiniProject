package com.secondminiproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.secondminiproject.entities.EnquiryStatus;
import com.secondminiproject.service.IEnqueryStatusService;

@Controller
public class StatusEnqueryController {

	@Autowired
	private IEnqueryStatusService enqueryStatusService;

	@GetMapping("/status")
	public List<String> getAllEnqueryStatus() {
		List<String> allEnqueryStatus = this.enqueryStatusService.getAllEnqueryStatus();
		return allEnqueryStatus;
		
	}
}
