package com.bimetri.products.registration.school.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import com.bimetri.products.registration.school.bean.CourseCategory;
import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.bean.model.Course;
import com.bimetri.products.registration.school.dao.DaoCourse;
import com.bimetri.products.registration.school.dao.repo.CourseRepository;
import com.bimetri.products.registration.school.exception.DaoException;

@Component("DaoCourse")
public class DaoCourseSpringDataJpaImpl extends DaoBase implements DaoCourse {

	@Autowired
	private CourseRepository courseRepo;

	@Override
	public List<DtoCourse> getAllCourses() throws DaoException {
		List<DtoCourse> result = new ArrayList<DtoCourse>();
		try {
			Iterable<Course> courses = courseRepo.findAll();

			courses.forEach(course -> {
				result.add(modelMapper.map(course, DtoCourse.class));
			});
		} catch (Exception e) {
			throw new DaoException("Unable to get all courses!", e);
		}
		return result;
	}

	@Override
	public List<DtoCourse> findByCategoryAndName(CourseCategory category, String courseName) throws DaoException {
		List<DtoCourse> result = new ArrayList<DtoCourse>();
		try {
			Course exampleInstance = new Course();
			exampleInstance.setCategory(category);
			exampleInstance.setCourseName(courseName);
			Example<Course> example = Example.of(exampleInstance, ExampleMatcher.matchingAll());
			List<Course> foundCourses = courseRepo.findAll(example);
			if (Objects.nonNull(foundCourses)) {
				foundCourses.forEach(foundCourse -> {
					result.add(modelMapper.map(foundCourse, DtoCourse.class));
				});
			}
		} catch (Exception e) {
			throw new DaoException("Unable to find courses by category and name", e);
		}
		return result;
	}

	@Override
	public DtoCourse findByCourseId(Integer id) throws DaoException {
		try {
			Optional<Course> opt = courseRepo.findById(id);
			if (opt.isPresent())
				return modelMapper.map(opt.get(), DtoCourse.class);
		} catch (Exception e) {
			throw new DaoException("Unable to find course by id", e);
		}
		return null;
	}

	@Override
	public DtoCourse saveCourse(DtoCourse dtoCourse) throws DaoException {
		try {
			dtoCourse.convertEmptyStringsToNull();
			Course model = modelMapper.map(dtoCourse, Course.class);
			if (Objects.nonNull(dtoCourse.getCourseId())) {
				// partial update
				Optional<Course> courseOpt = courseRepo.findById(dtoCourse.getCourseId());
				if (courseOpt.isPresent()) {
					Course existing = courseOpt.get();

					if (Objects.nonNull(model.getCategory()))
						existing.setCategory(model.getCategory());

					if (Objects.nonNull(model.getCourseName()))
						existing.setCourseName(model.getCourseName());

					if (Objects.nonNull(model.getCredits()))
						existing.setCredits(model.getCredits());

					if (Objects.nonNull(model.getClassNumber()))
						existing.setClassNumber(model.getClassNumber());

					if (Objects.nonNull(model.getRegistrations()))
						existing.setRegistrations(model.getRegistrations());

					model = existing;
				}
			}
			Course saved = courseRepo.save(model);
			DtoCourse dtoSaved = modelMapper.map(saved, DtoCourse.class);
			return dtoSaved;
		} catch (Exception e) {
			throw new DaoException("Unable to add or update course: " + dtoCourse.toString(), e);
		}
	}

	@Override
	public boolean deleteCourse(Integer id) throws DaoException {
		try {
			if (Objects.nonNull(id)) {
				Optional<Course> course = courseRepo.findById(id);
				if (course.isPresent()) {
					courseRepo.deleteById(id);
					return true;
				}
			}
		} catch (Exception e) {
			throw new DaoException("Unable to delete Course with id:" + id, e);
		}
		return false;
	}
}
