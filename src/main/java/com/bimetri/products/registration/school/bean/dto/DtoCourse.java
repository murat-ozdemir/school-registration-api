package com.bimetri.products.registration.school.bean.dto;

import java.util.List;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.CourseCategory;
import com.bimetri.products.registration.school.bean.DtoBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY)
@ApiModel(value = "Course CRUD Api Model", description ="Model")
public class DtoCourse extends DtoBase {
	private static final long serialVersionUID = 7120249970635884521L;

	@ApiModelProperty(value = "unique courseId field of course model")
	private Integer courseId;
	
	@NonNull 
	@ApiModelProperty(value = "non null category field of course model")
	private CourseCategory category;
	
	@NonNull 
	@ApiModelProperty(value = "non null courseName field of course model")
	private String courseName;
	
	@NonNull 
	@ApiModelProperty(value = "non null credits field of course model")
	private Short credits;
	
	@NonNull 
	@ApiModelProperty(value = "non null classNumber field of course model")
	private Short classNumber;
	
	@JsonIgnoreProperties("course")
	@ApiModelProperty(value = "registrations list field of course model")
	private List<DtoCourseRegistration> registrations;
}
