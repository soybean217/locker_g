package com.highguard.Wisdom.exception;

public class WisdomException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WisdomException() {
		super();
		
	}
	
	public WisdomException(String msg) {
		super(msg);
	}
	
	public WisdomException(String msg,Throwable cause) {
		super(msg,cause);
	}
	
	public WisdomException(Throwable cause) {
		super(cause);
	}
}
