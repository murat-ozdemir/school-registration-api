package com.bimetri.products.registration.school.bean.dto;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.DtoBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY)
@ApiModel(value = "Course Registration Query Model", description = "Model")
public class DtoCourseRegistration extends DtoBase {
	private static final long serialVersionUID = -4640489190089884779L;
	
	@ApiModelProperty(value = "unique registrationId field of course registration model")
	private Integer registrationId;
	
	@JsonIgnoreProperties("registrations")
	@NonNull
	@ApiModelProperty(value = "non null student field of course registration model")
	private DtoStudent student;
	
	@JsonIgnoreProperties("registrations")
	@NonNull
	@ApiModelProperty(value = "non null course field of course registration model")
	private DtoCourse course;
	
	@JsonFormat(pattern="dd.MM.yyyy-HH:mm:ss")
	@ApiModelProperty(value = "non null registrationTime field of course registration model")
	private LocalDateTime registrationTime;
}
