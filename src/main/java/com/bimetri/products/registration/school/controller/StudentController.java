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

import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.bean.response.ErrorDataResult;
import com.bimetri.products.registration.school.bean.response.ErrorResult;
import com.bimetri.products.registration.school.bean.response.Result;
import com.bimetri.products.registration.school.bean.response.SuccessDataResult;
import com.bimetri.products.registration.school.dao.DaoStudent;

@RestController
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	@Qualifier("DaoStudent")
	private DaoStudent daoStudent;

	@GetMapping("/student/list")
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
	public ResponseEntity<?> getStudent(@PathVariable Integer studentId) {
		try {
			DtoStudent student = daoStudent.findByStudentId(studentId);
			Result result = null;
			if (Objects.nonNull(student)) {
				result = new SuccessDataResult<DtoStudent>(student);
			} else {
				result = new ErrorResult("Student with id:" + studentId + " not found!");
			}
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error("Unable to get existing student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@PostMapping("/student/add")
	public ResponseEntity<?> addStudent(@RequestBody @Nullable DtoStudent dtoStudent) {
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
				return ResponseEntity.ok(result);
			}
		} catch (Exception e) {
			logger.error("Unable to add student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@PutMapping("/student/update")
	public ResponseEntity<?> updateStudent(@RequestBody @Nullable DtoStudent dtoStudent) {
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
				return ResponseEntity.badRequest().body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to update student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}

	@DeleteMapping("/student/{studentId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId) {
		try {
			boolean success = daoStudent.deleteStudent(studentId);
			Result result = null;
			if (success) {
				result = new SuccessDataResult<DtoStudent>("Student with id:" + studentId + " deleted.");
				return ResponseEntity.ok(result);
			} else {
				result = new ErrorResult("Student with id:" + studentId + " not found!");
				return ResponseEntity.badRequest().body(result);
			}
		} catch (Exception e) {
			logger.error("Unable to delete existing student!", e);
			ErrorResult result = new ErrorResult(e.getMessage());
			return ResponseEntity.internalServerError().body(result);
		}
	}
}
