package com.despegar.model;

public class Root {

	String id;
	Trace root;

	public String getId() {
		return id;
	}

	public void setId(String traceId) {
		this.id = traceId;
	}

	public Trace getRoot() {
		return root;
	}

	public void setRoot(Trace traceLog) {
		this.root = traceLog;
	}

}
