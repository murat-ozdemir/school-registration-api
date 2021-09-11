package com.bimetri.products.registration.school.dao;

import java.util.List;

import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.bean.dto.DtoCourseRegistration;
import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.exception.DaoException;

public interface DaoCourseRegistration {

	public DtoCourseRegistration saveRegistration(DtoCourseRegistration dtoRegistration) throws DaoException;

	public Integer studentCoursesCount(DtoStudent dtoStudent) throws DaoException;

	public Integer courseStudentsCount(DtoCourse dtoCourse) throws DaoException;

	public boolean deleteRegistration(Integer id) throws DaoException;
	
	public List<DtoStudent> findStudentsHavingNoCourseRegistration() throws DaoException;
	
	public List<DtoCourse> findCoursesHavingNoStudentsRegistered() throws DaoException;
}
