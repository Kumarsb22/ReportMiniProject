package com.secondminiproject.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.secondminiproject.entities.Course;
import com.secondminiproject.entities.EnquiryStatus;
import com.secondminiproject.repositories.CourseRepository;
import com.secondminiproject.repositories.EnqueryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnqueryStatusRepository enqueryStatusRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

//		this.courseRepository.deleteAll();
//		this.enqueryStatusRepository.deleteAll();
//
//		Course course = new Course();
//		course.setCousrseName("Java");
//
//		Course course2 = new Course();
//		course2.setCousrseName("AWS");
//
//		Course course3 = new Course();
//		course3.setCousrseName("Angular");
//
//		Course course4 = new Course();
//		course4.setCousrseName("DevOpps");
//
//		Course course5 = new Course();
//		course5.setCousrseName("Spring Boot and Microservices");
//
//		List<Course> asList = Arrays.asList(course, course2, course3, course4, course5);
//		this.courseRepository.saveAll(asList);
//
//		EnquiryStatus enquiryStatus = new EnquiryStatus();
//		enquiryStatus.setEnqueryStatusName("NEW");
//
//		EnquiryStatus enquiryStatus1 = new EnquiryStatus();
//		enquiryStatus1.setEnqueryStatusName("ENROLLED");
//
//		EnquiryStatus enquiryStatus2 = new EnquiryStatus();
//		enquiryStatus2.setEnqueryStatusName("LOST");
//
//		List<EnquiryStatus> asList2 = Arrays.asList(enquiryStatus, enquiryStatus1, enquiryStatus2);
//		this.enqueryStatusRepository.saveAll(asList2);
	}

}
