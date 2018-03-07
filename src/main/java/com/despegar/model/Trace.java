package com.despegar.model;

import java.util.List;

import com.despegar.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Trace {
	

	
	@JsonSerialize(using = JsonDateSerializer.class)
	java.util.Date start;

	@JsonSerialize(using = JsonDateSerializer.class)
	java.util.Date end;
	
	String service;
	
	String span;

	List<Trace> calls;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
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

	public List<Trace> getCalls() {
		return calls;
	}

	public void setCalls(List<Trace> calls) {
		this.calls = calls;
	}

}
