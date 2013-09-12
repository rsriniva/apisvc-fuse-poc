package com.redhat.bashburn.fuse;

public class HostTransformer {
	private String from;
	private String to;

	public String doTransform(String body) {
		return body.replaceAll(getFrom(), getTo());
	}

	public String getFrom() { return this.from; }
	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() { return this.to; }
	public void setTo(String to) {
		this.to = to;
	}
}