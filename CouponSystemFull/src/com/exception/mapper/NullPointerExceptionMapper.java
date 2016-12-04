package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.helpers.Message;
import com.helpers.ServerLogPrint;

@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException>{

	@Override
	public Response toResponse(NullPointerException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message("NullPointerException", 500);
		// print it to server log
		ex.printStackTrace();
		ServerLogPrint.messegeLog("NullPointerException", "NullPointerException");
		// return response to the User
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
	}

}
