package com.weblogic.rest.xdo.util;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.weblogic.rest.xdo.exception.WeblogicFileAccessException;

 
public final class FileResourceUtility {

	private static Logger logger = Logger.getLogger(FileResourceUtility.class);
	
	
	private FileResourceUtility(){}
	
	public static InputStream getResourceAsInputStream(String resource) throws WeblogicFileAccessException{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		if (classLoader == null){
			classLoader = FileResourceUtility.class.getClassLoader();
		}
		
		try {
			InputStream stream = classLoader.getResourceAsStream(resource);
			
			return stream;
		} catch (Exception e){
			logger.fatal("Cannot load resource: " + resource, e);
			throw new WeblogicFileAccessException("Cannot load resource: " + resource, e);
		}
		 
	}
	
	public static String getResourceAsString(String resource) throws WeblogicFileAccessException{
		StringBuilder sb = new StringBuilder();		
		InputStream stream = getResourceAsInputStream(resource);
		
		if (stream == null){
			logger.fatal("Cannot load resource: " + resource);
			throw new WeblogicFileAccessException("Cannot load resource: " + resource);
		}
		
		Scanner scanner = new Scanner(stream);
		
		while (scanner.hasNextLine()){
			sb.append(scanner.nextLine() + "\n");
		}
		
		return sb.substring(0,(sb.length() == 0)?0:(sb.length() - 1));
	}
	
}
