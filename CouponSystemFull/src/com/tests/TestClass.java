package com.tests;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("/test")
public class TestClass {

	public TestClass() {	
	}
	
	@GET
	@Path("/setm")
	@Produces(MediaType.TEXT_PLAIN)
	public String setm(@QueryParam("messege") String messege, @QueryParam("name") String name) {
		return "Messege from: " + name + " and he says= " + messege;
	}
	
	@GET
	@Path("/getm")
	@Produces(MediaType.TEXT_PLAIN)
	public String getm() {
		return "This is getMessege Function";
	}
	
//	@GET
//	@Path("/getParsedObject")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getParsedObject(@QueryParam("query") JsonParams json) {
//		Object o = json.getParsedObject();
//		return o.toString();
//	}

}
