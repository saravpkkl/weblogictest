package com.weblogic.rest.xdo.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.weblogic.rest.xdo.exception.WeblogicXdoException;
import com.weblogic.rest.xdo.util.WeblogicXdoUtility;


@Path("report")
public class ReportGeneratorRest {

	private static final Logger logger = Logger.getLogger(ReportGeneratorRest.class);

	
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateReport(
    	@QueryParam("output") @DefaultValue("pdf") String outputFormat,
        @FormDataParam("dataxml") InputStream dataXmlStream,
        @FormDataParam("dataxml") FormDataContentDisposition dataXmlDetails,
        @FormDataParam("templatexsl") InputStream templateXslStream,
        @FormDataParam("templatexsl") FormDataContentDisposition templateXslDetails) throws WeblogicXdoException {

		if (!outputFormat.equals("pdf") && !outputFormat.equals("rtf")){
			throw new WeblogicXdoException("Output format " + outputFormat + " is not supported.");
		}
		
		WeblogicXdoUtility.validateStream(dataXmlStream, "dataxml");
		WeblogicXdoUtility.validateStream(templateXslStream, "templatexsl");
		
		logger.debug("Data XML: " + dataXmlDetails.getFileName());
		logger.debug("Template XSL: " + templateXslDetails.getFileName());

		StreamingOutput reportStream = getReportStream(dataXmlStream,
				templateXslStream,
				outputFormat.equals("pdf") ? WeblogicXdoUtility.FORMAT_PDF
						: WeblogicXdoUtility.FORMAT_RTF);

		return Response.ok(reportStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition",
						"attachment; filename = "
								+ WeblogicXdoUtility.getOutFilename(dataXmlDetails
										.getFileName()) + "." + outputFormat)
				.build();
    }
	
	
	public StreamingOutput getReportStream(
			final InputStream dataXmlStream, 
			final InputStream templateXslStream, 
			final byte outputFormat){
		return new StreamingOutput() {
			public void write(OutputStream out) throws IOException, WebApplicationException {
				try{
					WeblogicXdoUtility.generateReport(dataXmlStream, templateXslStream, out, outputFormat);
				} catch(WeblogicXdoException xdoEx){
					throw new WebApplicationException(xdoEx);
				}			
			}
		};
	}
	
}
