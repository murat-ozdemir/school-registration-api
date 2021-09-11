package com.bimetri.products.registration.school.bean.dto;

import java.util.List;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.DtoBase;
import com.bimetri.products.registration.school.bean.Sex;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY) //@JsonRootName(value = "student")
public class DtoStudent extends DtoBase {
	private static final long serialVersionUID = 6136240456930052103L;
	
	private Integer studentId;
	
	@NonNull 
	private String name;
	
	@NonNull 
	private String surname;
	
	@NonNull 
	private String schoolClass;
	
	@NonNull 
	private Short age;
	
	@NonNull 
	private Sex sex;
	
	private List<DtoCourseRegistration> registrations;
}
