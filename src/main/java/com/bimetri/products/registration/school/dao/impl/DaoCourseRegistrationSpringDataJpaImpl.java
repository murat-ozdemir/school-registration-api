package com.bimetri.products.registration.school.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.bean.dto.DtoCourseRegistration;
import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.bean.model.Course;
import com.bimetri.products.registration.school.bean.model.CourseRegistration;
import com.bimetri.products.registration.school.bean.model.Student;
import com.bimetri.products.registration.school.dao.DaoCourseRegistration;
import com.bimetri.products.registration.school.dao.repo.CourseRegistrationRepository;
import com.bimetri.products.registration.school.exception.DaoException;

@Component("DaoCourseRegistration")
public class DaoCourseRegistrationSpringDataJpaImpl extends DaoBase implements DaoCourseRegistration {

	@Autowired
	private CourseRegistrationRepository repository;

	@Override
	public DtoCourseRegistration saveRegistration(DtoCourseRegistration dtoRegistration) throws DaoException {
		try {
			CourseRegistration registration = modelMapper.map(dtoRegistration, CourseRegistration.class);
			CourseRegistration saved = repository.save(registration);
			return modelMapper.map(saved, DtoCourseRegistration.class);
		} catch (Exception e) {
			throw new DaoException("Unable to add or update course registration: " + dtoRegistration.toString(), e);
		}
	}

	@Override
	public boolean deleteRegistration(Integer id) throws DaoException {
		if (Objects.nonNull(id)) {
			Optional<CourseRegistration> registration = repository.findById(id);
			if (registration.isPresent()) {
				repository.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer studentCoursesCount(DtoStudent dtoStudent) throws DaoException {
		return repository.countByStudentStudentId(dtoStudent.getStudentId());
	}

	@Override
	public Integer courseStudentsCount(DtoCourse dtoCourse) throws DaoException {
		return repository.countByCourseCourseId(dtoCourse.getCourseId());
	}
	
	@Override
	public List<DtoCourse> findCoursesHavingNoStudentsRegistered() throws DaoException {
		List<DtoCourse> dtoCourses = new ArrayList<DtoCourse>();
		List<Course> courses = repository.findCoursesHavingNoStudentsRegistered();
		if ( Objects.nonNull(courses) ) {
			courses.forEach(course -> {
				dtoCourses.add(modelMapper.map(course, DtoCourse.class));
			});
		}
		return dtoCourses;
	}
	
	@Override
	public List<DtoStudent> findStudentsHavingNoCourseRegistration() throws DaoException {
		List<DtoStudent> dtoStudents = new ArrayList<DtoStudent>();
		List<Student> students = repository.findStudentsHavingNoCourseRegistration();
		if ( Objects.nonNull(students) ) {
			students.forEach(student -> {
				dtoStudents.add(modelMapper.map(student, DtoStudent.class));
			});
		}
		return dtoStudents;
	}

}
