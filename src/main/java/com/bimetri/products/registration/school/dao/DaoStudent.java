package com.bimetri.products.registration.school.dao;

import java.util.List;

import com.bimetri.products.registration.school.bean.dto.DtoStudent;
import com.bimetri.products.registration.school.exception.DaoException;

public interface DaoStudent {

	public List<DtoStudent> getAllStudents() throws DaoException;
	
	public List<DtoStudent> findByNameAndSurname(String name, String surname) throws DaoException;
	
	public DtoStudent findByStudentId(Integer id) throws DaoException;
	
	public DtoStudent saveStudent(DtoStudent dtoStudent) throws DaoException;
	
	public boolean deleteStudent(DtoStudent dtoStudent) throws DaoException;
}
