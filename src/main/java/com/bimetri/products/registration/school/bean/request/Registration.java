package com.bimetri.products.registration.school.bean.request;

import org.springframework.lang.NonNull;

import com.bimetri.products.registration.school.bean.DtoBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @JsonInclude(Include.NON_EMPTY)
@ApiModel(value = "Registration Request Api Model", description = "Model")
public class Registration extends DtoBase {

	private static final long serialVersionUID = 1926279127852598443L;

	@NonNull
	@ApiModelProperty(value = "non null studentId field of registration request")
	private Integer studentId;
	
	@NonNull
	@ApiModelProperty(value = "non null courseId field of registration request")
	private Integer courseId;
}
