package com.weblogic.rest.xdo.exception;

 
public class WeblogicXdoException extends Exception {

	private static final long serialVersionUID = 3391831530005384337L;

	
	public WeblogicXdoException(){
		super();
	}
	
	public WeblogicXdoException(Throwable t){
		super(t);
	}
	
	public WeblogicXdoException(String str, Throwable t){
		super(str,t);
	}
	
	public WeblogicXdoException(String str){
		super(str);
	}
	
}
