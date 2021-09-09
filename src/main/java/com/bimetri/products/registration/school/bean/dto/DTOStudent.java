package com.bimetri.products.registration.school.bean.dto;

import java.util.List;

import com.bimetri.products.registration.school.bean.DTOBase;
import com.bimetri.products.registration.school.bean.Sex;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DTOStudent extends DTOBase {
	private static final long serialVersionUID = 6136240456930052103L;
	
	private Integer studentId;
	private String name;
	private String surname;
	private String schoolClass;
	private Short age;
	private Sex sex;
	private List<DTOCourse> courses;
}
