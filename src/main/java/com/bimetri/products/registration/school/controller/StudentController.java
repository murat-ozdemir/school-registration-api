package com.bimetri.products.registration.school.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.bean.response.ErrorDataResult;
import com.bimetri.products.registration.school.bean.response.ErrorResult;
import com.bimetri.products.registration.school.bean.response.Result;
import com.bimetri.products.registration.school.bean.response.SuccessDataResult;
import com.bimetri.products.registration.school.dao.DaoStudent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Student CRUD Api", tags = {"Student CRUD Api"})
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	@Qualifier("DaoStudent")
	private DaoStudent daoStudent;

	@GetMapping("/student/list")
	@ApiOperation(value = "Get All Students", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Student list in 'data' field")
	})
	public ResponseEntity<?> getAllStudents() {
		try {
			List<DtoStudent> existingStudents = daoStudent.getAllStudents();
			SuccessDataResult<List<DtoStudent>> result = null;

			if (existingStudents.isEmpty())
				result = new SuccessDataResult<>(existingStudents, "No students found");
			else
				result = new SuccessDataResult<>(existingStudents);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error("Unable to get existing students!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@GetMapping("/student/{studentId}")
	@ApiOperation(value = "Get Student By Id", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 404, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Student model in 'data' field")
	})
	public ResponseEntity<?> getStudent(@PathVariable Integer studentId) {
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
			logger.error("Unable to get existing student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@PostMapping("/student/add")
	@ApiOperation(value = "Add Student", produces = "application/json", 
		notes = "WARNING! Student.schoolClass must contain both 1 char class number and 1 char branch, Eg. '3B' or '2A'")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorDataResult.class, code = 400, message = "ErrorDataResult model with Students list in 'data' description"),
			@ApiResponse(response = ErrorResult.class, code = 400, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 201, message = "SuccessDataResult model with Student model in 'data' field")
	})
	public ResponseEntity<?> addStudent(@RequestBody @Nullable 
			@ApiParam(name = "Student", value = "New student definition without studentId and registrations fields", required = true) 
			DtoStudent dtoStudent) {
		try {
			if (Objects.isNull(dtoStudent))
				return ResponseEntity.badRequest().body(new ErrorResult("Student information could not be empty!"));

			if (Objects.isNull(dtoStudent.getName()) || Objects.isNull(dtoStudent.getSurname())) {
				return ResponseEntity.badRequest().body(new ErrorResult("Both name and surname must be filled!"));
			}

			List<DtoStudent> existingStudents = daoStudent.findByNameAndSurname(dtoStudent.getName(),
					dtoStudent.getSurname());
			if (Objects.nonNull(existingStudents) && !existingStudents.isEmpty()) {
				ErrorDataResult<List<DtoStudent>> result = new ErrorDataResult<List<DtoStudent>>(existingStudents,
						"Same students found with same name and surname");
				return ResponseEntity.badRequest().body(result);
			} else {
				DtoStudent saved = daoStudent.saveStudent(dtoStudent);
				SuccessDataResult<DtoStudent> result = new SuccessDataResult<DtoStudent>(saved, "added");
				return ResponseEntity.status(HttpStatus.CREATED).body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to add student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@PutMapping("/student/update")
	@ApiOperation(value = "Update Student", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 400, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with Student model in 'data' field")
	})
	public ResponseEntity<?> updateStudent(@RequestBody @Nullable 
			@ApiParam(name = "Student", value = "Student definition to be updated with studentId and required updating fields only", required = true) 
			DtoStudent dtoStudent) {
		try {
			if (Objects.isNull(dtoStudent))
				return ResponseEntity.badRequest().body(new ErrorResult("Student information could not be empty!"));

			if (Objects.isNull(dtoStudent.getStudentId())) {
				return ResponseEntity.badRequest().body(new ErrorResult("Student Id must be filled!"));
			}

			DtoStudent existingStudent = daoStudent.findByStudentId(dtoStudent.getStudentId());
			if (Objects.nonNull(existingStudent)) {
				DtoStudent saved = daoStudent.saveStudent(dtoStudent);
				SuccessDataResult<DtoStudent> result = new SuccessDataResult<DtoStudent>(saved, "updated");
				return ResponseEntity.ok(result);
			} else {
				ErrorResult result = new ErrorResult("Student with id:" + dtoStudent.getStudentId() + " not found!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to update student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@DeleteMapping("/student/delete/{studentId}")
	@ApiOperation(value = "Delete Student By Id", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(response = ErrorResult.class, code = 400, message = "ErrorResult model with description"),
			@ApiResponse(response = ErrorResult.class, code = 500, message = "ErrorResult model with description"),
			@ApiResponse(response = SuccessDataResult.class, code = 200, message = "SuccessDataResult model with message in 'data' field")
	})
	public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId) {
		try {
			boolean success = daoStudent.deleteStudent(studentId);
			Result result = null;
			if (success) {
				result = new SuccessDataResult<DtoStudent>("Student with id:" + studentId + " deleted.");
				return ResponseEntity.ok(result);
			} else {
				result = new ErrorResult("Student with id:" + studentId + " not found!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to delete existing student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}
}
