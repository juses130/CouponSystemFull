package com.exceptionerrors;

import javax.xml.bind.annotation.XmlRootElement;



public class FiledErrorException extends Exception{
	private static final long serialVersionUID = 1L;

	// Default Constructor
	public FiledErrorException(){
		super(); //
	}
	
	public FiledErrorException(String message){
		super(message);
	}
}
