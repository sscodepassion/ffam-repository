package com.ffam.workdistapi.exceptions;

public class TaskAssignmentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String errorDescription;
	
	private final String errorCode;
	
	public TaskAssignmentException(String errorCode, String errorDescription) {
		this(errorCode, errorDescription, null);
	}	
	
	public TaskAssignmentException(String errorCode, String errorDescription, Throwable cause) {
		super(errorDescription, cause);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}
}