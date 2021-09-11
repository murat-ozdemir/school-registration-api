package com.bimetri.products.registration.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.dao.DaoStudent;
import com.bimetri.products.registration.school.exception.BloException;
import com.bimetri.products.registration.school.exception.DaoException;
import com.bimetri.products.registration.school.service.StudentService;

@Component("StudentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	@Qualifier("DaoStudent")
	private DaoStudent daoStudent;
	
	@Override
	public DtoStudent findStudentById(Integer id) throws BloException {
		try {
			return daoStudent.findByStudentId(id);
		} catch (DaoException e) {
			throw new BloException(e);
		}
	}

}
