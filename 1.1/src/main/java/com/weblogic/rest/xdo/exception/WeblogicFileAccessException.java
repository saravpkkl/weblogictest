package com.weblogic.rest.xdo.exception;



public class WeblogicFileAccessException extends Exception {
	
	private static final long serialVersionUID = 6761896457240079629L;

	
	public WeblogicFileAccessException() {
		super();
	}

	public WeblogicFileAccessException(Throwable t) {
		super(t);
	}

	public WeblogicFileAccessException(String str, Throwable t) {
		super(str, t);
	}

	public WeblogicFileAccessException(String str) {
		super(str);
	}

}
