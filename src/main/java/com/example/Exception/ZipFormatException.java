package com.example.Exception;

public class ZipFormatException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	public ZipFormatException(String message) {
		super(message);
		this.setMessage(message);
	}

	public ZipFormatException() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
