package com.helpers;

import java.time.LocalDateTime;

public class ServerLogPrint {

	
	private ServerLogPrint() {}

	// Methods
	
	//TODO: JavaDoc to this method - only for errors
	public static void messegeLog(String method ,String messege) {
		
		if(messege != null && !messege.isEmpty()) {
			System.out.println("Server Log: " + "[" +  method + "] " + messege + " " + LocalDateTime.now()) ;	
		}
		
	}
	
	// TODO: JavaDocs to this method (wow.. I'm high as shit.. right now.. XD)  - this method is if the response pharsing an object and in the succed ONLY
	public static void objectLog(String method ,Object object) {
		
		if(object != null && !object.toString().isEmpty()) {
			System.out.println("Server Log: " + "[" +  method + "] " + object.toString() + " " + LocalDateTime.now()) ;	
		}
		
	}
} // class
