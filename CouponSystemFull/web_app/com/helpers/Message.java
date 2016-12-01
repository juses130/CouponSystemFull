package com.helpers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

/**
 * With is class we can send messages as object and parsing them into XML_TEXT (or anything else). is just a helper class. </b>
 * @author Raziel
 *
 */
public class Message {

	// Attributes
	private String message;
	private int codeMessage;
	
	// Constructor
	public Message() {}
	
	public Message(String message, int codeMessage) {
		super();
		this.message = message;
		this.codeMessage = codeMessage;
	} // Constructor
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(int codeMessage) {
		this.codeMessage = codeMessage;
	} // setErrorCode
	
	public Response getStatusUNATHORIZED(String method) {
		String userMessege = "UNAUTHORIZED";
		Message messageForParsing = new Message(userMessege, 401);
		ServerLogPrint.messegeLog(method ,userMessege);
		
		return Response.status(Status.UNAUTHORIZED).entity(messageForParsing).build();
	}
	
	public Response getStatusUNCustom(String method, String message) {
		String userMessege = "UNAUTHORIZED: " + message;
		Message messageForParsing = new Message(userMessege, 401);
		ServerLogPrint.messegeLog(method ,userMessege);
		
		return Response.status(Status.UNAUTHORIZED).entity(messageForParsing).build();
	}
	
	public Response getStatusOK(String method) {
		String userMessege = "OK";
		// Return  XML_TEXT / JSON to the user by storing the userMessage in Object.
		Message messageForParsing = new Message(userMessege, 200);
		ServerLogPrint.messegeLog(method ,userMessege);
		
		return Response.status(Status.OK).entity(messageForParsing).build();
	}
	
	public Response getMethodStatusOK(String method, Object object) {
		// Return  XML_TEXT / JSON to the user by storing the userMessage in Object.
		Message messageForParsing = new Message(object.toString(), 200);
		ServerLogPrint.objectLog(method, object);
		
		return Response.status(Status.OK).entity(messageForParsing).build();
	}
	
	public Response getStatusOKCustom(String method, String message) {
//		String userMessege = "OK: " + message;
		Message messageForParsing = new Message(message, 200);
		ServerLogPrint.messegeLog(method ,message);
		
		return Response.status(Status.OK).entity(messageForParsing).build();
	}
	
	public Response getStatusOKPureBulild(String method, Object object) {
//		String userMessege = "OK: " + message;
//		Message messageForParsing = new Message(message, 401);
		
		if(object != null && method != null) { 
			ServerLogPrint.objectLog(method, object);
		}
		
		return Response.status(Status.OK).entity(object).build();
	}
	
	public Response getStatusUNlogin(String method) {
		String userMessege = "UNAUTHORIZED";
		Message messageForParsing = new Message(userMessege, 302);
		ServerLogPrint.messegeLog(method ,userMessege);
		
		return Response.status(Status.UNAUTHORIZED).entity(messageForParsing).build();
	}
	
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return '{' + '"' + "codeMessage:" + codeMessage + "," + '"' + "message" + '"' + ":" + message + '"' + '}' ;
//	}
	
} // Class
