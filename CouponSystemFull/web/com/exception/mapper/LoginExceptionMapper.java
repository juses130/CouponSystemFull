package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.exceptionerrors.LoginException;
import com.helpers.Message;
import com.helpers.ServerLogPrint;

@Provider
public class LoginExceptionMapper implements ExceptionMapper<LoginException>{

	@Override
	public Response toResponse(LoginException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message(ex.getMessage(), 400);
		// print it to server log
		ServerLogPrint.messegeLog("LoginException", error.getMessage());
		// return response to the User
		return Response.status(Status.UNAUTHORIZED).entity(error).build();
	}

	

}