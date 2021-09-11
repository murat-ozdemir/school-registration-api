package com.bimetri.products.registration.school.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.bean.response.ErrorDataResult;
import com.bimetri.products.registration.school.bean.response.ErrorResult;
import com.bimetri.products.registration.school.bean.response.Result;
import com.bimetri.products.registration.school.bean.response.SuccessDataResult;
import com.bimetri.products.registration.school.dao.DaoCourse;
import com.bimetri.products.registration.school.service.CourseService;

@RestController
public class CourseController {
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	@Qualifier("DaoCourse")
	private DaoCourse daoCourse;
	
	@Autowired
	@Qualifier("CourseService")
	private CourseService courseService;

	@GetMapping("/course/list")
	public ResponseEntity<?> getAllCourses() {
		try {
			List<DtoCourse> existingCourses = daoCourse.getAllCourses();
			SuccessDataResult<List<DtoCourse>> result = null;

			if (existingCourses.isEmpty())
				result = new SuccessDataResult<>(existingCourses, "No courses found");
			else
				result = new SuccessDataResult<>(existingCourses);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error("Unable to get existing courses!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@GetMapping("/course/{courseId}")
	public ResponseEntity<?> getCourse(@PathVariable Integer courseId) {
		try {
			DtoCourse course = courseService.findCourseById(courseId);
			Result result = null;
			if ( Objects.nonNull(course)) {
				result = new SuccessDataResult<DtoCourse>(course);
			} else {
				result = new ErrorResult("Course with id:" + courseId + " not found!");
			}
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error("Unable to get existing course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@PostMapping("/course/add")
	public ResponseEntity<?> addCourse(@RequestBody @Nullable DtoCourse dtoCourse) {
		try {
			if (Objects.isNull(dtoCourse))
				return ResponseEntity.badRequest().body(new ErrorResult("Course information could not be empty!"));

			if (Objects.isNull(dtoCourse.getCategory()) || Objects.isNull(dtoCourse.getCourseName())) {
				return ResponseEntity.badRequest().body(new ErrorResult("Both category and name must be filled!"));
			}

			List<DtoCourse> existingCourses = daoCourse.findByCategoryAndName(dtoCourse.getCategory(),
					dtoCourse.getCourseName());
			if (Objects.nonNull(existingCourses) && !existingCourses.isEmpty()) {
				ErrorDataResult<List<DtoCourse>> result = new ErrorDataResult<List<DtoCourse>>(existingCourses,
						"Same courses found with same category and name");
				return ResponseEntity.badRequest().body(result);
			} else {
				DtoCourse saved = daoCourse.saveCourse(dtoCourse);
				SuccessDataResult<DtoCourse> result = new SuccessDataResult<DtoCourse>(saved, "added");
				return ResponseEntity.ok(result);
			}
		} catch (Exception e) {
			logger.error("Unable to add course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@PutMapping("/course/update")
	public ResponseEntity<?> updateCourse(@RequestBody @Nullable DtoCourse dtoCourse) {
		try {
			if (Objects.isNull(dtoCourse))
				return ResponseEntity.badRequest().body(new ErrorResult("Course information could not be empty!"));

			if (Objects.isNull(dtoCourse.getCourseId())) {
				return ResponseEntity.badRequest().body(new ErrorResult("Course Id must be filled!"));
			}

			DtoCourse existingCourse = daoCourse.findByCourseId(dtoCourse.getCourseId());
			if (Objects.nonNull(existingCourse)) {
				DtoCourse saved = daoCourse.saveCourse(dtoCourse);
				SuccessDataResult<DtoCourse> result = new SuccessDataResult<DtoCourse>(saved, "updated");
				return ResponseEntity.ok(result);
			} else {
				ErrorResult result = new ErrorResult("Course with id:" + dtoCourse.getCourseId() + " not found!");
				return ResponseEntity.badRequest().body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to update course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}
	
	@DeleteMapping("/course/{courseId}")
	public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId) {
		try {
			boolean success = daoCourse.deleteCourse(courseId);
			Result result = null;
			if (success) {
				result = new SuccessDataResult<DtoCourse>("Course with id:" + courseId + " deleted.");
				return ResponseEntity.ok(result);
			} else {
				result = new ErrorResult("Course with id:" + courseId + " not found!");
				return ResponseEntity.badRequest().body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to delete existing course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}
}
