package com.bimetri.products.registration.school.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bimetri.products.registration.school.bean.dto.DTOStudent;
import com.bimetri.products.registration.school.bean.model.Student;
import com.bimetri.products.registration.school.dao.DAOStudent;
import com.bimetri.products.registration.school.dao.repo.StudentRepository;
import com.bimetri.products.registration.school.exception.DaoException;

@Component("DAOStudent")
public class DAOStudentSpringDataJpaImpl extends DAOBase implements DAOStudent {

	@Autowired
	private StudentRepository studentRepo;

	@Override
	public List<DTOStudent> getAllStudents() throws DaoException {
		List<DTOStudent> result = new ArrayList<DTOStudent>();
		Iterable<Student> students = studentRepo.findAll();

		students.forEach(student -> {
			result.add(modelMapper.map(student, DTOStudent.class));
		});
		return result;
	}
}
