package com.bimetri.products.registration.school.dao;

import java.util.List;

import com.bimetri.products.registration.school.bean.CourseCategory;
import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.exception.DaoException;

public interface DaoCourse {

	public List<DtoCourse> getAllCourses() throws DaoException;
	
	public List<DtoCourse> findByCategoryAndName(CourseCategory category, String courseName) throws DaoException;
	
	public DtoCourse findByCourseId(Integer id) throws DaoException;
	
	public DtoCourse saveCourse(DtoCourse dtoCourse) throws DaoException;
	
	public boolean deleteCourse(DtoCourse dtoCourse) throws DaoException;
}
