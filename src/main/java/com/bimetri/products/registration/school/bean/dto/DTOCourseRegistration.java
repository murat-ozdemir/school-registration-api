package com.bimetri.products.registration.school.bean.dto;

import java.time.LocalDateTime;

import com.bimetri.products.registration.school.bean.DTOBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DTOCourseRegistration extends DTOBase {
	private static final long serialVersionUID = -4640489190089884779L;
	
	private Integer registrationId;
	private DTOStudent student;
	private DTOCourse course;
	private LocalDateTime registerationTime;
    private int grade;

}
