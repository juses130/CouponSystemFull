package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.exceptions.messeges.ServiceException;
import com.helpers.Message;
import com.helpers.ServerLogPrint;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException>{

	@Override
	public Response toResponse(ServiceException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message(ex.getMessage(), 400);
		// print it to server log
		ServerLogPrint.messegeLog("ServiceException", error.getMessage());
		// return response to the User
		return Response.status(Status.BAD_GATEWAY).entity(error).build();
	}

}
