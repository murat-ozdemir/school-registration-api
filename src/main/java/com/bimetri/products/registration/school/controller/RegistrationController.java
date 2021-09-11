package com.bimetri.products.registration.school.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.bean.dto.DtoCourseRegistration;
import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.bean.request.Registration;
import com.bimetri.products.registration.school.bean.response.ErrorDataResult;
import com.bimetri.products.registration.school.bean.response.ErrorResult;
import com.bimetri.products.registration.school.bean.response.Result;
import com.bimetri.products.registration.school.bean.response.SuccessDataResult;
import com.bimetri.products.registration.school.dao.DaoCourse;
import com.bimetri.products.registration.school.dao.DaoCourseRegistration;
import com.bimetri.products.registration.school.dao.DaoStudent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Student Course Registration Api", tags = { "Student Course Registration Api" })
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	@Qualifier("DaoStudent")
	private DaoStudent daoStudent;

	@Autowired
	@Qualifier("DaoCourse")
	private DaoCourse daoCourse;

	@Autowired
	@Qualifier("DaoCourseRegistration")
	private DaoCourseRegistration daoRegistration;

	@Value("${application.student.course.limit}")
	private Integer STUDENT_COURSE_LIMIT;

	@Value("${application.course.student.limit}")
	private Integer COURSE_STUDENT_LIMIT;

	@PostMapping("/registration/add")
	@ApiOperation(value = "Register Student To Existing Course", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorDataResult.class, code = 400, message = "ErrorDataResult model with Student or Course model in 'data' field"),
			@ApiResponse(response = ErrorResult.class, code = 404, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 201, message = "SuccessDataResult model with Course Registration model in 'data' field")
	})
	public ResponseEntity<?> register(@RequestBody @Nullable 
			@ApiParam(name = "Registration Request", value = "Contains Student Id and Course Id (Greater Than 0)", required = true)
			Registration registration) {
		try {
			if (Objects.isNull(registration))
				return ResponseEntity.badRequest()
						.body(new ErrorResult("Registration information could not be empty!"));

			if (Objects.isNull(registration.getStudentId()) || Objects.isNull(registration.getCourseId())) {
				return ResponseEntity.badRequest().body(new ErrorResult("Both studentId and courseId must be filled!"));
			}

			DtoStudent student = daoStudent.findByStudentId(registration.getStudentId());
			if (Objects.isNull(student)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResult("Could not find student with id:" + registration.getStudentId()));
			}

			if ( Objects.nonNull(student.getRegistrations()) ) {
				for( DtoCourseRegistration reg: student.getRegistrations() ) {
					if ( reg.getCourse().getCourseId().equals(registration.getCourseId())) {
						return ResponseEntity.badRequest().body(new ErrorDataResult<DtoStudent>(student, 
								"Student is already registered to course with Id:" + registration.getCourseId() ) );						
					}
				}
			}
			
			DtoCourse course = daoCourse.findByCourseId(registration.getCourseId());
			if (Objects.isNull(course)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResult("Could not find course with id:" + registration.getCourseId()));
			}

			int studentCoursesCount = daoRegistration.studentCoursesCount(student);
			if (studentCoursesCount >= STUDENT_COURSE_LIMIT) {
				return ResponseEntity.badRequest()
						.body(new ErrorDataResult<DtoStudent>(student, "Student courses is reached to maximum limit!"));
			}

			int courseStudentsCount = daoRegistration.courseStudentsCount(course);
			if (courseStudentsCount >= COURSE_STUDENT_LIMIT) {
				return ResponseEntity.badRequest()
						.body(new ErrorDataResult<DtoCourse>(course, "Course students is reached to maximum limit!"));
			}

			int studentClassNumber = 0;
			try {
				studentClassNumber = Integer.parseInt(student.getSchoolClass().substring(0, 1));
			} catch (NumberFormatException e) {
				logger.error("Could not parse student's class number:" + student.toString());
			}
			String message = null;
			if (course.getClassNumber().intValue() != studentClassNumber) {
				message = "WARNING! Student class number is not compatible with course class number!";
			} else {
				message = "registered";
			}
			DtoCourseRegistration dtoRegistration = new DtoCourseRegistration();
			dtoRegistration.setCourse(course);
			dtoRegistration.setStudent(student);
			dtoRegistration.setRegistrationTime(LocalDateTime.now());
			DtoCourseRegistration saved = daoRegistration.saveRegistration(dtoRegistration);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessDataResult<DtoCourseRegistration>(saved, message));

		} catch (Exception e) {
			logger.error("Unable to register student to the course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@GetMapping("/registration/course/{courseId}")
	@ApiOperation(value = "Get Students Registered To A Course", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 404, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Course model in 'data' field")
	})
	public ResponseEntity<?> getStudentsByCourse(@PathVariable Integer courseId) {
		try {
			DtoCourse course = daoCourse.findByCourseId(courseId);
			Result result = null;
			if (Objects.nonNull(course)) {
				result = new SuccessDataResult<DtoCourse>(course);
				return ResponseEntity.ok(result);
			} else {
				result = new ErrorResult("Course with id:" + courseId + " not found!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to get students for the course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@GetMapping("/registration/student/{studentId}")
	@ApiOperation(value = "Get Courses Which A Student Registered", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 404, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Student model in 'data' field")
	})
	public ResponseEntity<?> getCoursesByStudent(@PathVariable Integer studentId) {
		try {
			DtoStudent student = daoStudent.findByStudentId(studentId);
			Result result = null;
			if (Objects.nonNull(student)) {
				result = new SuccessDataResult<DtoStudent>(student);
				return ResponseEntity.ok(result);
			} else {
				result = new ErrorResult("Student with id:" + studentId + " not found!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to get courses for the student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@GetMapping("/registration/student/unregistered")
	@ApiOperation(value = "Get Students Without Any Courses", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 404, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Course list in 'data' field")
	})
	public ResponseEntity<?> getCoursesWithoutAnyStudent() {
		try {
			List<DtoCourse> courses = daoRegistration.findCoursesHavingNoStudentsRegistered();
			if (Objects.nonNull(courses) && !courses.isEmpty()) {
				return ResponseEntity.ok(new SuccessDataResult<>(courses));
			} else {
				return ResponseEntity.ok(new ErrorResult("All courses have at least one student or there are no courses!"));
			}
		} catch (Exception e) {
			logger.error("Unable to get students have not registered to any course!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@GetMapping("/registration/course/unregistered")
	@ApiOperation(value = "Get Courses Without Any Students", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 404, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Student list in 'data' field")
	})
	public ResponseEntity<?> getStudentsWithoutAnyCourses() {
		try {
			List<DtoStudent> students = daoRegistration.findStudentsHavingNoCourseRegistration();
			if (Objects.nonNull(students) && !students.isEmpty()) {
				return ResponseEntity.ok(new SuccessDataResult<>(students));
			} else {
				return ResponseEntity.ok(new ErrorResult("All students registered at least one course or there are no students!"));
			}
		} catch (Exception e) {
			logger.error("Unable to get courses do not have any student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}
}
