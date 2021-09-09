package com.bimetri.products.registration.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bimetri.products.registration.school.dao.DAOStudent;

@RestController
@RequestMapping(name = "/api/user")
public class UserController {

	@Autowired
	@Qualifier("DAOStudent")
	private DAOStudent daoStudent;
	
	
}
