package com.bimetri.products.registration.school.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SuccessResult extends Result {
	public SuccessResult(boolean success) {
		super(true);
	}

	public SuccessResult(String message) {
		super(true, message);
	}
}
