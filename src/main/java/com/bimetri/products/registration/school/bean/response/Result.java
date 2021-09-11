package com.bimetri.products.registration.school.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Result {
	private Boolean success;
	private String message;

	public Result(boolean success) {
		this.success = success;
	}

	public Result(Boolean success, String message) {
		this(success);
		this.message = message;
	}

	public Boolean isSuccess() {
		return this.success;
	}

	public String getMessage() {
		return this.message;
	}
}
