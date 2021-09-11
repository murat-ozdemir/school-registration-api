package com.bimetri.products.registration.school.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DataResult<T> extends Result {
	private T data;

	public DataResult(T data, Boolean success) {
		super(success);
		this.data = data;
	}

	public DataResult(T data, Boolean success, String message) {
		super(success, message);
		this.data = data;
	}

	public T getData() {
		return this.data;
	}
}
