package com.bimetri.products.registration.school.bean.dto;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.DtoBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY)
public class DtoCourseRegistration extends DtoBase {
	private static final long serialVersionUID = -4640489190089884779L;
	
	private Integer registrationId;
	private DtoStudent student;
	private DtoCourse course;
	
	@NonNull 
	@JsonFormat(pattern="dd.MM.yyyy-HH:mm:ss")
	private LocalDateTime registerationTime;
    
	@NonNull 
	private int grade;

}
