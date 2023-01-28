package com.weblogic.rest.xdo.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.weblogic.rest.xdo.exception.WeblogicExceptionMapper;
import com.weblogic.rest.xdo.resource.ReportGeneratorRest;
import com.weblogic.rest.xdo.resource.XslGeneratorRest;

public class WeblogicApplication extends Application {

	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> classes = new HashSet<Class<?>>();
		
		classes.add(ReportGeneratorRest.class);		
		classes.add(WeblogicExceptionMapper.class);
		
		return classes;
	}
	
}
