package com.bimetri.products.registration.school.bean.dto;

import java.util.List;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.DtoBase;
import com.bimetri.products.registration.school.bean.Sex;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY) //@JsonRootName(value = "student")
@ApiModel(value = "Student CRUD Api Model", description = "Model")
public class DtoStudent extends DtoBase {
	private static final long serialVersionUID = 6136240456930052103L;
	
	@ApiModelProperty(value = "unique studentId field of student model")
	private Integer studentId;
	
	@NonNull 
	@ApiModelProperty(value = "non null name field of student model")
	private String name;
	
	@NonNull 
	@ApiModelProperty(value = "non null surname field of student model")
	private String surname;
	
	@NonNull 
	@ApiModelProperty(value = "non null schoolClass field of student model")
	private String schoolClass;
	
	@NonNull 
	@ApiModelProperty(value = "non null age field of student model")
	private Short age;
	
	@NonNull 
	@ApiModelProperty(value = "non null sex field of student model")
	private Sex sex;
	
	@JsonIgnoreProperties("student")
	@ApiModelProperty(value = "registrations list field of student model")
	private List<DtoCourseRegistration> registrations;
}
