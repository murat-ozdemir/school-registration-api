package com.bimetri.products.registration.school.bean.dto;

import java.util.List;

import com.bimetri.products.registration.school.bean.CourseCategory;
import com.bimetri.products.registration.school.bean.DTOBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DTOCourse extends DTOBase {
	private static final long serialVersionUID = 7120249970635884521L;

	private Integer courseId;
	private CourseCategory category;
	private String courseName;
	private Short credits;
	private Short classNumber;
	private List<DTOStudent> students;
}
