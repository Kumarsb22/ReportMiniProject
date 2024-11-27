package com.secondminiproject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.secondminiproject.bindingclasses.DashboardResponse;
import com.secondminiproject.bindingclasses.EnquiryForm;
import com.secondminiproject.bindingclasses.EnquirySearchCriteria;
import com.secondminiproject.constants.AppConstants;
import com.secondminiproject.entities.StudentEnqueries;
import com.secondminiproject.entities.User;
import com.secondminiproject.repositories.StudentEnqueryRespository;
import com.secondminiproject.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class StudentEnqueryServiceImpl implements IStudentService {

	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnqueryRespository enqueryRespository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public DashboardResponse getDashboardDate(Integer userId) {

		// create the dashboard object\
		DashboardResponse dashboardResponse = new DashboardResponse();
		User user = null;
		Optional<User> findById = this.userRepository.findById(userId);
		if (findById.isPresent()) {
			user = findById.get();
		}

		// get all enquires and cout it
		List<StudentEnqueries> enqueries = user.getEnqueries();
		int totalCnt = enqueries.size();

		// filter enquires based on the status enrolled and count it
		int totalEnrolledCnt = enqueries.stream().filter(e -> e.getEnqueryStatus().equals("ENROLLED"))
				.collect(Collectors.toList()).size();

		// filter enquires based on the status lost and count it
		int lostCnt = enqueries.stream().filter(e -> e.getEnqueryStatus().equals("LOST")).collect(Collectors.toList())
				.size();

		dashboardResponse.setTotalEnquriesCnt(totalCnt);
		dashboardResponse.setLostCnt(lostCnt);
		dashboardResponse.setEnrolledCnt(totalEnrolledCnt);

		return dashboardResponse;
	}

	@Override
	public String addEnquiry(EnquiryForm enquiryForm) {

		// to save quiries we need create quiries class object
		StudentEnqueries studentEnqueries = new StudentEnqueries();
		User user = null;
		if (enquiryForm.getEnqueryId() != null && !"".equals(enquiryForm.getEnqueryId())) {
			studentEnqueries.setEnqueryId(enquiryForm.getEnqueryId());
		} else {
			return AppConstants.STR_FIELD_CANNOT_BE_EMPTY;
		}

		if (enquiryForm.getStudentName() != null && !"".equals(enquiryForm.getStudentName())) {
			studentEnqueries.setStudentName(enquiryForm.getStudentName());
		} else {
			return AppConstants.STR_FIELD_CANNOT_BE_EMPTY;
		}
		if (enquiryForm.getPhone() != null && !"".equals(enquiryForm.getPhone())) {
			studentEnqueries.setPhone(enquiryForm.getPhone());
		} else {
			return AppConstants.STR_FIELD_CANNOT_BE_EMPTY;
		}
		if (enquiryForm.getClassMode() != null && !"".equals(enquiryForm.getClassMode())) {
			studentEnqueries.setClassMode(enquiryForm.getClassMode());
		} else {
			return AppConstants.STR_FIELD_CANNOT_BE_EMPTY;
		}
		if (enquiryForm.getCourseName() != null && !"".equals(enquiryForm.getCourseName())) {
			studentEnqueries.setCourseName(enquiryForm.getCourseName());
		} else {
			return AppConstants.STR_FIELD_CANNOT_BE_EMPTY;
		}
		if (enquiryForm.getEnqueryStatus() != null && !"".equals(enquiryForm.getEnqueryStatus())) {
			studentEnqueries.setEnqueryStatus(enquiryForm.getEnqueryStatus());
		} else {
			return AppConstants.STR_FIELD_CANNOT_BE_EMPTY;
		}

		studentEnqueries.setCreatedDate(LocalDate.now());
		// save enquiry in database and we have to set userid of who is saving this
		// enquiries
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USERID);

		if (userId == null) {
			return "redirect:logout";
		}
		Optional<User> findById = this.userRepository.findById(userId);

		if (findById.isPresent()) {
			user = findById.get();
		}

		studentEnqueries.setUser(user);

		this.enqueryRespository.save(studentEnqueries);

		return AppConstants.STR_SUCCESS;
	}

	@Override
	public List<StudentEnqueries> getEnquires(Integer userId, EnquirySearchCriteria criteria) {
//Example jpa query function
		StudentEnqueries enqueries = new StudentEnqueries();

		if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
			enqueries.setClassMode(criteria.getClassMode());
		}
		if (null != criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
			enqueries.setCourseName(criteria.getCourseName());
		}
		if (null != criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {
			enqueries.setEnqueryStatus(criteria.getEnqStatus());
		}

		List<StudentEnqueries> findAll = this.enqueryRespository.findAll(Example.of(enqueries));

		// stream filter approach
		Optional<User> findById = this.userRepository.findById(userId);
		if (findById.isPresent()) {
			User user = findById.get();
			List<StudentEnqueries> enqueriesStudentEnqueries = user.getEnqueries();

			// filter logic
			if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enqueriesStudentEnqueries = enqueriesStudentEnqueries.stream()
						.filter(e -> e.getClassMode().equals(criteria.getClassMode())).collect(Collectors.toList());

			}
			if (null != criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
				enqueriesStudentEnqueries = enqueriesStudentEnqueries.stream()
						.filter(e -> e.getCourseName().equals(criteria.getCourseName())).collect(Collectors.toList());

			}
			if (null != criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {
				enqueriesStudentEnqueries = enqueriesStudentEnqueries.stream()
						.filter(e -> e.getEnqueryStatus().equals(criteria.getEnqStatus())).collect(Collectors.toList());
			}
			return enqueriesStudentEnqueries;
		}
		return null;

//		List<EnquiryForm> enquiryForms = new ArrayList<>();
//
//		BeanUtils.copyProperties(findAll, enquiryForms);
		// return findAll;
	}

	@Override
	public List<StudentEnqueries> getEnquires() {

		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USERID);

		Optional<User> findById = this.userRepository.findById(userId);

		if (findById.isPresent()) {
			User user = findById.get();
			List<StudentEnqueries> enqueries = user.getEnqueries();
			return enqueries;
		}
		return null;
	}

	@Override
	public StudentEnqueries getEnqById(Integer enqueryId) {
		Optional<StudentEnqueries> findById = this.enqueryRespository.findById(enqueryId);
		StudentEnqueries studentEnqueries = null;
		if (findById.isPresent()) {
			studentEnqueries = findById.get();
		}

		return studentEnqueries;
	}

}
