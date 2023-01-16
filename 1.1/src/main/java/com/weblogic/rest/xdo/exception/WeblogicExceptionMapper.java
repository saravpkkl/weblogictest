package com.weblogic.rest.xdo.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;


public class WeblogicExceptionMapper implements ExceptionMapper<Exception> {
	
	private static final Logger logger = Logger.getLogger(WeblogicExceptionMapper.class);	
	
	
	public Response toResponse(Exception ex) {
		logger.debug("Exception raised to MfgRestExceptionMapper: ", ex);
		
		int status;
		
		if (ex instanceof WebApplicationException){
			status = ((WebApplicationException) ex).getResponse().getStatus();
		}
		else {
		    status = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		}
		
		RequestError error = new RequestError();
		
		
		error.setHttpResponseCode(status);
		error.setSummary(ex.getMessage());
		error.setUserMessage(ex.getLocalizedMessage());
		error.setDetail(ex.getClass().getName());
		
		return Response.status(status).entity(error).type(MediaType.APPLICATION_XML).build();
	}
	
}
