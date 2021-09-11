package com.bimetri.products.registration.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.dao.DaoCourse;
import com.bimetri.products.registration.school.exception.BloException;
import com.bimetri.products.registration.school.exception.DaoException;
import com.bimetri.products.registration.school.service.CourseService;

@Component("CourseService")
public class CourseServiceImpl implements CourseService {

	@Autowired
	@Qualifier("DaoCourse")
	private DaoCourse daoCourse;
	
	@Override
	public DtoCourse findCourseById(Integer id) throws BloException {
		try {
			return daoCourse.findByCourseId(id);
		} catch (DaoException e) {
			throw new BloException(e);
		}
	}

}
