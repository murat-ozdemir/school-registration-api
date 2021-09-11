package com.bimetri.products.registration.school.exception;


public class DaoException extends Exception {
	private static final long serialVersionUID = -2726329642517237662L;

	public DaoException() {
		super();
	}
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(Throwable t) {
		super(t);
	}
	
	public DaoException(String message, Throwable t) {
		super(message, t);
	}

}
