package com.exceptions.messeges;

public class DataNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7899761522423884477L;

	public DataNotFoundException() {
		super();
	}
	
	public DataNotFoundException(String messege) {
		super(messege);
	}
}
