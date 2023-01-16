package com.weblogic.rest.xdo.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.template.FOProcessor;
import oracle.apps.xdo.template.RTFProcessor;

import org.apache.log4j.Logger;

import com.weblogic.rest.xdo.exception.WeblogicFileAccessException;
import com.weblogic.rest.xdo.exception.WeblogicXdoException;


public class WeblogicXdoUtility {

	private static final Logger logger = Logger.getLogger(WeblogicXdoUtility.class);
	
	
	public static final byte FORMAT_PDF = FOProcessor.FORMAT_PDF;
	public static final byte FORMAT_RTF = FOProcessor.FORMAT_RTF;
	
	private static final Pattern fileNamePattern = Pattern.compile("(.*)\\.[^\\.]*$");
	
	
	public static void generateXslFromRtfTemplate(InputStream rtfTemplate, OutputStream out) throws WeblogicXdoException{
		try{
			RTFProcessor rtfProcessor = new RTFProcessor(rtfTemplate);
			rtfProcessor.setOutput(out);
			rtfProcessor.process();
		} catch (XDOException xdoEx){
			logger.error("Error while transforming RTF to XSL", xdoEx);
			throw new WeblogicXdoException(xdoEx);
		}		
	}
	
	public static void generateReport(InputStream dataXml, InputStream xslTemplate, OutputStream out, byte outputFormat) throws WeblogicXdoException{
		try{
			logger.debug("Output Format: "
					+ ((outputFormat == FORMAT_PDF) ? "PDF"
							: ((outputFormat == FORMAT_RTF) ? "RTF" : "invalid (" + outputFormat + ")")));
			
			FOProcessor processor = new FOProcessor();
			processor.setData(dataXml);
			processor.setTemplate(xslTemplate);
			processor.setOutput(out);
			processor.setOutputFormat(outputFormat);
			
			// Load xdo.cfg for Font Mappings
			try{
				processor.setConfig(FileResourceUtility.getResourceAsInputStream("xdo.cfg"));
			} catch (WeblogicFileAccessException faEx){
				logger.error("Cannot read xdo.cfg", faEx);
				throw new WeblogicXdoException(faEx);
			}
			
			processor.generate();
		} catch (XDOException xdoEx){
			logger.error("Error while generating report", xdoEx);
			throw new WeblogicXdoException(xdoEx);
		}
	}
	
	public static void validateStream(InputStream input, String streamName) throws WeblogicXdoException{
		if (input == null){
			throw new WeblogicXdoException("\"" + streamName + "\" is missing from Request");
		}
	}
	
	public static String getOutFilename(String fileName){
		Matcher matcher = fileNamePattern.matcher(fileName);
		if (matcher.find()){
			return matcher.group(1);
		}
		else {
			return fileName;
		}
	}
}
