package com.bimetri.products.registration.school.service;

import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.exception.BloException;

public interface StudentService {

	public DtoStudent findStudentById ( Integer id ) throws BloException;
}
