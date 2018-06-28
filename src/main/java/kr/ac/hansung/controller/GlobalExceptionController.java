package kr.ac.hansung.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.ac.hansung.exception.ErrorResponse;
import kr.ac.hansung.exception.UserDuplicatedException;
import kr.ac.hansung.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionController {
	
	// --- Error Response
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse>
		handlerUserNotFoundException(HttpServletRequest request, UserNotFoundException ex){
		
		String requestURL = request.getRequestURI().toString();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setRequestURL(requestURL);
		errorResponse.setErrorCode("user.notfound.exception");
		errorResponse.setErrorMsg("User with id " + ex.getUserId() + " not found");
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	// --- Error Response
	@ExceptionHandler(UserDuplicatedException.class)
	public ResponseEntity<ErrorResponse>
		handlerUserDuplicatedException(HttpServletRequest request, UserDuplicatedException ex){
		
		String requestURL = request.getRequestURI().toString();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setRequestURL(requestURL);
		errorResponse.setErrorCode("user.duplicated.exception");
		errorResponse.setErrorMsg("Unable to create. A user with name " + ex.getUsername() + " already exist");
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
	}
}
