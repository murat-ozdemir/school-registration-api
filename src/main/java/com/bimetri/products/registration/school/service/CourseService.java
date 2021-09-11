package com.bimetri.products.registration.school.service;

import com.bimetri.products.registration.school.bean.dto.DtoCourse;
import com.bimetri.products.registration.school.exception.BloException;

public interface CourseService {

	public DtoCourse findCourseById ( Integer id ) throws BloException;
}
