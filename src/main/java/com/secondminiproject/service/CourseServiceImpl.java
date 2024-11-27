package com.secondminiproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secondminiproject.entities.Course;
//import com.secondminiproject.entities.Course;
import com.secondminiproject.repositories.CourseRepository;

@Service
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<String> getAllCourse() {
		List<Course> courses = this.courseRepository.findAll();

		ArrayList<String> courseName = new ArrayList<>();
		for (Course course : courses) {
			courseName.add(course.getCousrseName());

		}
		return courseName;
	}

}
