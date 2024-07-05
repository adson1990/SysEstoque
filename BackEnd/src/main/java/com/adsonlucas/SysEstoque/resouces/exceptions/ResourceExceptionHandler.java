package com.adsonlucas.SysEstoque.resouces.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeExistenteException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(EntidadeNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntidadeNotFoundException e,
														HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setError("Objeto não encontrado com o ID informado.");
		error.setMsg(e.getMessage());
		error.setStatus(status.value());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataInconsistencyException(DataBaseException e,
																	HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setError("Dados consistidos e com relacionamento no DB");
		error.setMsg(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setStatus(status.value());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> dataValidation(MethodArgumentNotValidException e,
																	HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		ValidationError error = new ValidationError();
		error.setTimeStamp(Instant.now());
		error.setError("Validation Exception.");
		error.setMsg(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setStatus(status.value());
		
		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			error.addError(f.getField(), f.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(EntidadeExistenteException.class)
	public ResponseEntity<StandardError> entityFoundException(EntidadeExistenteException e,
																	HttpServletRequest request){
		HttpStatus status = HttpStatus.FOUND;
		
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setError("Objeto já existe na base de dados.");
		error.setMsg(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setStatus(status.value());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<StandardError> badCredentialsException(BadCredentialsException e,
																	HttpServletRequest request){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setError("Usuário e/ou senha incorretos.");
		error.setMsg(e.getMessage());
		error.setPath(request.getRequestURI());
		error.setStatus(status.value());
		
		return ResponseEntity.status(status).body(error);
	}

}
