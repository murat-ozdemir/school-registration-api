package com.bimetri.products.registration.school.bean.dto;

import java.util.List;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.CourseCategory;
import com.bimetri.products.registration.school.bean.DtoBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY)
public class DtoCourse extends DtoBase {
	private static final long serialVersionUID = 7120249970635884521L;

	private Integer courseId;
	
	@NonNull 
	private CourseCategory category;
	
	@NonNull 
	private String courseName;
	
	@NonNull 
	private Short credits;
	
	@NonNull 
	private Short classNumber;
	
	@JsonIgnoreProperties("course")
	private List<DtoCourseRegistration> registrations;
}
