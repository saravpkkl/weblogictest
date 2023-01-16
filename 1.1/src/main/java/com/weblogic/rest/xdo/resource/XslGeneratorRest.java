package com.weblogic.rest.xdo.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.weblogic.rest.xdo.exception.WeblogicXdoException;
import com.weblogic.rest.xdo.util.WeblogicXdoUtility;

 
@Path("template")
public class XslGeneratorRest {
	
	private static final Logger logger = Logger.getLogger(XslGeneratorRest.class);
	
	
	@POST
	@Path("rtf/convert")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateReport(
        @FormDataParam("templatertf") final InputStream templateRtfStream,
        @FormDataParam("templatertf") final FormDataContentDisposition templateRtfDetails) throws WeblogicXdoException {

		WeblogicXdoUtility.validateStream(templateRtfStream, "templatertf");
		
		logger.debug("RTF Template: " + templateRtfDetails.getFileName());

		StreamingOutput xslStream = new StreamingOutput() {
			
			public void write(OutputStream out) throws IOException, WebApplicationException {
				try{
					WeblogicXdoUtility.generateXslFromRtfTemplate(templateRtfStream, out);
				} catch(WeblogicXdoException xdoEx){
					throw new WebApplicationException(xdoEx);
				}
			}
		}; 
				
		return Response.ok(xslStream, MediaType.APPLICATION_OCTET_STREAM)
        			.header("Content-Disposition", "attachment; filename = " + WeblogicXdoUtility.getOutFilename(templateRtfDetails.getFileName()) + ".xsl")
        			.build();
    }
	
}
