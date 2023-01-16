package com.weblogic.rest.xdo.exception;

import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class RequestError {
	
    private Integer httpResponseCode;
    private String summary;
    private String userMessage;
    private String detail;
	
    
    public Integer getHttpResponseCode() {
		return httpResponseCode;
	}
	
    public void setHttpResponseCode(Integer httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}
	
    public String getSummary() {
		return summary;
	}
	
    public void setSummary(String summary) {
		this.summary = summary;
	}
	
    public String getUserMessage() {
		return userMessage;
	}
	
    public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	
    public String getDetail() {
		return detail;
	}
	
    public void setDetail(String detail) {
		this.detail = detail;
	}
    
}
