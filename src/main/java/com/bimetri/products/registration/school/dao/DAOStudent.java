package com.bimetri.products.registration.school.dao;

import java.util.List;

import com.bimetri.products.registration.school.bean.dto.DTOStudent;
import com.bimetri.products.registration.school.exception.DaoException;

public interface DAOStudent {

	public List<DTOStudent> getAllStudents() throws DaoException;
	
	public boolean saveStudent(DTOStudent dtoStudent) throws DaoException;
	
	public boolean deleteStudent(DTOStudent dtoStudent) throws DaoException;
}
