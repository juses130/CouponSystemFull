package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.exceptionerrors.DaoException;
import com.helpers.Message;
import com.helpers.ServerLogPrint;

@Provider
public class DaoExceptionMapper implements ExceptionMapper<DaoException>{

	@Override
	public Response toResponse(DaoException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message(ex.getMessage(), 404);
		// print it to server log
		ServerLogPrint.messegeLog("DaoException", error.getMessage());
		// return response to the User
		return Response.status(Status.NOT_FOUND).entity(error).build();

	}

}
