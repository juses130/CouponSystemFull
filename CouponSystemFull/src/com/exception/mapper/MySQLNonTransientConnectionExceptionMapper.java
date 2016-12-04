package com.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.helpers.Message;
import com.helpers.ServerLogPrint;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;

@Provider
public class MySQLNonTransientConnectionExceptionMapper implements ExceptionMapper<MySQLNonTransientConnectionException>{

	@Override
	public Response toResponse(MySQLNonTransientConnectionException ex) {
		// This is ErrorMessege (helpers class)
		Message error = new Message(ex.getMessage(), 504);
		// print it to server log
		ServerLogPrint.messegeLog("MySQLNonTransientConnectionException", error.getMessage());
		// return response to the User
		return Response.status(Status.GATEWAY_TIMEOUT).entity(error).build();

	}

}
