package com.ffam.workdistapi.exceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorMessage {

	@JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")	
	private LocalDateTime errorTimeStamp;
	
	private String message;
	
	private String debugMessage;
	
	protected ErrorMessage() {
		errorTimeStamp = LocalDateTime.now();
	}
	
	public ErrorMessage(Throwable ex) {
		this();
		this.message = "Unexpected Error";
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	public ErrorMessage(String message, Throwable ex) {
		this(ex);
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public LocalDateTime getErrorTimeStamp() {
		return errorTimeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}
}