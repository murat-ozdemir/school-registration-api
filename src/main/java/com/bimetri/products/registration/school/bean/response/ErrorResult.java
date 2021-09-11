package com.bimetri.products.registration.school.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorResult extends Result {
	public ErrorResult(boolean success) {
		super(false);
	}

	public ErrorResult(String message) {
		super(false, message);
	}
}
