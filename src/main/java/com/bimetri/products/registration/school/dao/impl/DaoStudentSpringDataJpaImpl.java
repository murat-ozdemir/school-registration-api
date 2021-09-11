package com.bimetri.products.registration.school.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.bean.model.Student;
import com.bimetri.products.registration.school.dao.DaoStudent;
import com.bimetri.products.registration.school.dao.repo.StudentRepository;
import com.bimetri.products.registration.school.exception.DaoException;

@Component("DaoStudent")
public class DaoStudentSpringDataJpaImpl extends DaoBase implements DaoStudent {
	@Autowired
	private StudentRepository studentRepo;

	@Override
	public List<DtoStudent> getAllStudents() throws DaoException {
		List<DtoStudent> result = new ArrayList<DtoStudent>();
		try {
			Iterable<Student> students = studentRepo.findAll();

			students.forEach(student -> {
				result.add(modelMapper.map(student, DtoStudent.class));
			});
		} catch (Exception e) {
			throw new DaoException("Unable to get all students!", e);
		}
		return result;
	}

	@Override
	public List<DtoStudent> findByNameAndSurname(String name, String surname) throws DaoException {
		List<DtoStudent> result = new ArrayList<DtoStudent>();
		try {
			Student exampleInstance = new Student();
			exampleInstance.setName(name);
			exampleInstance.setSurname(surname);
			Example<Student> example = Example.of(exampleInstance, ExampleMatcher.matchingAll());
			List<Student> foundStudents = studentRepo.findAll(example);
			if (Objects.nonNull(foundStudents)) {
				foundStudents.forEach(foundStudent -> {
					result.add(modelMapper.map(foundStudent, DtoStudent.class));
				});
			}
		} catch (Exception e) {
			throw new DaoException("Unable to find students by name and surname", e);
		}
		return result;
	}

	@Override
	public DtoStudent findByStudentId(Integer id) throws DaoException {
		try {
			Optional<Student> opt = studentRepo.findById(id);
			if (opt.isPresent())
				return modelMapper.map(opt.get(), DtoStudent.class);
		} catch (Exception e) {
			throw new DaoException("Unable to find student by id", e);
		}
		return null;
	}

	@Override
	public DtoStudent saveStudent(DtoStudent dtoStudent) throws DaoException {
		try {
			dtoStudent.convertEmptyStringsToNull();
			Student model = modelMapper.map(dtoStudent, Student.class);
			if (Objects.nonNull(dtoStudent.getStudentId())) {
				// partial update
				Optional<Student> stOpt = studentRepo.findById(dtoStudent.getStudentId());
				if (stOpt.isPresent()) {
					Student existing = stOpt.get();
					if (Objects.nonNull(model.getName()))
						existing.setName(model.getName());

					if (Objects.nonNull(model.getSurname()))
						existing.setSurname(model.getSurname());

					if (Objects.nonNull(model.getAge()))
						existing.setAge(model.getAge());

					if (Objects.nonNull(model.getSchoolClass()))
						existing.setSchoolClass(model.getSchoolClass());

					if (Objects.nonNull(model.getSex()))
						existing.setSex(model.getSex());

					if (Objects.nonNull(model.getRegistrations()))
						existing.setRegistrations(model.getRegistrations());

					model = existing;
				}
			}
			Student saved = studentRepo.save(model);
			DtoStudent dtoSaved = modelMapper.map(saved, DtoStudent.class);
			return dtoSaved;
		} catch (Exception e) {
			throw new DaoException("Unable to add or update student: " + dtoStudent.toString(), e);
		}
	}

	@Override
	public boolean deleteStudent(Integer id) throws DaoException {
		try {
			if (Objects.nonNull(id)) {
				Optional<Student> student = studentRepo.findById(id);
				if ( student.isPresent() ) {
					studentRepo.deleteById(id);
					return true;					
				}
				return false;
			}
		} catch (Exception e) {
			throw new DaoException("Unable to delete student with id: " + id, e);
		}
		return false;
	}
}
