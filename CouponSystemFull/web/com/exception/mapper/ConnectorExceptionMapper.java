package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.exceptionerrors.ConnectorException;
import com.helpers.Message;
import com.helpers.ServerLogPrint;

@Provider
public class ConnectorExceptionMapper implements ExceptionMapper<ConnectorException>{

	@Override
	public Response toResponse(ConnectorException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message(ex.getMessage(), 500);
		// print it to server log
		ServerLogPrint.messegeLog("ConnectorException", error.getMessage());
		// return response to the User
		return Response.status(Status.CONFLICT).entity(error).build();
	}
	
	

}
