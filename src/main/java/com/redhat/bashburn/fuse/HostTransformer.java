package com.redhat.bashburn.fuse;

public class HostTransformer {
	public String doTransform(String body) {
		return body.replaceAll("localhost:8181/cxf", "localhost:8282/apimanager");
	}
}