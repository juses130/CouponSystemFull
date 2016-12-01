package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.exceptionerrors.FiledErrorException;
import com.helpers.Message;
import com.helpers.ServerLogPrint;

@Provider
public class FiledErrorExceptionMapper implements ExceptionMapper<FiledErrorException>{

	@Override
	public Response toResponse(FiledErrorException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message(ex.getMessage(), 400);
		// print it to server log
		ServerLogPrint.messegeLog("FiledErrorException", error.getMessage());
		// return response to the User
		return Response.status(Status.NOT_FOUND).entity(error).build();
	}

}
