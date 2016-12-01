package com.exceptions.messeges;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceException extends Exception {

	private static final long serialVersionUID = -8923224571670609196L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String messege) {
		super(messege);
	}

}
