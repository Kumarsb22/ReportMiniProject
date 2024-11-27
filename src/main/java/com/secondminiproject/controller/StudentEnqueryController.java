package com.secondminiproject.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secondminiproject.bindingclasses.DashboardResponse;
import com.secondminiproject.bindingclasses.EnquiryForm;
import com.secondminiproject.bindingclasses.EnquirySearchCriteria;
import com.secondminiproject.constants.AppConstants;
import com.secondminiproject.entities.StudentEnqueries;
import com.secondminiproject.service.ICourseService;
import com.secondminiproject.service.IEnqueryStatusService;
import com.secondminiproject.service.IStudentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentEnqueryController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private IStudentService iStudentService;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private IEnqueryStatusService enqueryStatusService;

	@GetMapping("/addenq")
	public String addEnquiryPage(Model model) {
		model.addAttribute(AppConstants.STR_ADDENQ, new EnquiryForm());

		List<String> status = this.enqueryStatusService.getAllEnqueryStatus();
		model.addAttribute(AppConstants.STR_STATUSE, status);

		List<String> course = this.courseService.getAllCourse();
		model.addAttribute(AppConstants.STR_COURSE, course);
		return AppConstants.STR_ADD_ENQ;

	}

	@GetMapping("/edit/{id}")
	public String editEnq(@PathVariable("id") Integer enqueryId, Model model) {
		System.out.println(enqueryId);
		StudentEnqueries enqueries = this.iStudentService.getEnqById(enqueryId);
		EnquiryForm enquiryForm = new EnquiryForm();
		BeanUtils.copyProperties(enqueries, enquiryForm);
		model.addAttribute(AppConstants.STR_ADDENQ, enquiryForm);

		List<String> status = this.enqueryStatusService.getAllEnqueryStatus();
		model.addAttribute(AppConstants.STR_STATUSE, status);

		List<String> course = this.courseService.getAllCourse();
		model.addAttribute(AppConstants.STR_COURSE, course);

		return AppConstants.STR_ADD_ENQ;

	}

	@PostMapping("/addenq")
	public String addEnq(@ModelAttribute("addenq") EnquiryForm enquiryForm, Model model) {

		String addEnquiry = this.iStudentService.addEnquiry(enquiryForm);

		if (addEnquiry.contains(AppConstants.STR_SUCCESS)) {
			model.addAttribute("succmsg", AppConstants.STR_ENQ_ADDED_SUCCESSFULLY);
		} else {
			model.addAttribute("errmsg", AppConstants.STR_FIELD_CANNOT_BE_EMPTY);
		}
		return AppConstants.STR_ADD_ENQ;

	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userid = (Integer) httpSession.getAttribute(AppConstants.STR_USERID);
		DashboardResponse dashboardresponse = this.iStudentService.getDashboardDate(userid);
		model.addAttribute("dashboard", dashboardresponse);

		return "dashboard";
	}

	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {

		init(model);

		List<StudentEnqueries> enquires = this.iStudentService.getEnquires();
		model.addAttribute(AppConstants.STR_ENQUIRIES, enquires);

		return "view-enquiries";
	}

	public void init(Model model) {

		List<String> status = this.enqueryStatusService.getAllEnqueryStatus();
		model.addAttribute(AppConstants.STR_STATUSE, status);

		List<String> course = this.courseService.getAllCourse();
		model.addAttribute(AppConstants.STR_COURSE, course);

	}

	@GetMapping("/filter-enquires")
	public String getFilteredEqueries(@RequestParam String cname, @RequestParam String status,

			@RequestParam String cmode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setClassMode(cmode);
		criteria.setCourseName(cname);
		criteria.setEnqStatus(status);

		System.out.println(criteria);
		Integer useraId = (Integer) httpSession.getAttribute(AppConstants.STR_USERID);
		List<StudentEnqueries> enquires = this.iStudentService.getEnquires(useraId, criteria);
		System.out.println(enquires);
		for (StudentEnqueries studentEnqueries : enquires) {
			System.out.println(studentEnqueries.getCourseName() + " " + studentEnqueries.getEnqueryStatus());
		}
		model.addAttribute(AppConstants.STR_ENQUIRIES, enquires);

		return "filter-enquiries-page";

	}

}
