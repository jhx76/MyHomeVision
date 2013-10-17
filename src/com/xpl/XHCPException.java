package com.xpl;

public class XHCPException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public XHCPException(String message) {
		super(message);
	}
	
	public XHCPException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public XHCPException(Throwable cause) {
		super(cause);
	}
	
}
