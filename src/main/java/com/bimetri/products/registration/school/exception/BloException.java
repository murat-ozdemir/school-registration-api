package com.bimetri.products.registration.school.exception;


public class BloException extends Exception {
	private static final long serialVersionUID = -2726329642517237662L;

	public BloException() {
		super();
	}
	
	public BloException(String message) {
		super(message);
	}
	
	public BloException(Throwable t) {
		super(t);
	}
	
	public BloException(String message, Throwable t) {
		super(message, t);
	}

}
