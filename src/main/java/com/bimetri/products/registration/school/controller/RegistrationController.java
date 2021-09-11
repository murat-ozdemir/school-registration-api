package com.bimetri.products.registration.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import com.bimetri.products.registration.school.dao.DaoCourse;
import com.bimetri.products.registration.school.dao.DaoStudent;

@RestController
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger( RegistrationController.class );
	
	@Autowired
	@Qualifier("DaoStudent")
	private DaoStudent daoStudent;
	
	@Autowired
	@Qualifier("DaoCourse")
	private DaoCourse daoCourse;
	
	
}
