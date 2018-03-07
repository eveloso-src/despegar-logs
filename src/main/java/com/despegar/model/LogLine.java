package com.despegar.model;

import com.despegar.util.JsonDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class LogLine {

	String traceId;
	
	String service;
	
	String span;
	
	String source;
	
	String destination;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	java.util.Date start;

	@JsonSerialize(using = JsonDateSerializer.class)
	java.util.Date end;

	LogLine calls;
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public java.util.Date getStart() {
		return start;
	}

	public void setStart(java.util.Date start) {
		this.start = start;
	}

	public java.util.Date getEnd() {
		return end;
	}

	public void setEnd(java.util.Date end) {
		this.end = end;
	}

	public LogLine getCalls() {
		return calls;
	}

	public void setCalls(LogLine calls) {
		this.calls = calls;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	

	

}
