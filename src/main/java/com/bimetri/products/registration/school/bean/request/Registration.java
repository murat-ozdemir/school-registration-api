package com.bimetri.products.registration.school.bean.request;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.DtoBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY)
public class Registration extends DtoBase {

	private static final long serialVersionUID = 1926279127852598443L;

	@NonNull 
	private Integer studentId;
	
	@NonNull 
	private Integer courseId;
}
