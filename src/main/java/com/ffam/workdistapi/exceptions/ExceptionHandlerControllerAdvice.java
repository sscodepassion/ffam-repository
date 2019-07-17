package com.ffam.workdistapi.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
	
	@RequestMapping(produces = "application/json")
	@ExceptionHandler (Exception.class)
	public ResponseEntity<Object> handleDefaultException(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex);
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(produces = "application/json")
	@ExceptionHandler (ResponseStatusException.class)
	public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex);
		return new ResponseEntity<>(errorMessage, ex.getStatus());
	}

	@RequestMapping(produces = "application/json")
	@ExceptionHandler (TaskAssignmentException.class)
	public ResponseEntity<Object> handleTaskAssignmentException(TaskAssignmentException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getErrorDescription(), ex);
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
}
