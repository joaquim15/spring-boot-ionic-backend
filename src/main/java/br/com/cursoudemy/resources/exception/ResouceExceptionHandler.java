package br.com.cursoudemy.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.cursoudemy.services.exceptions.DataIntegrityExeption;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@ControllerAdvice
public class ResouceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundExeption.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundExeption e, HttpServletRequest request) {

		StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityExeption.class)
	public ResponseEntity<StandartError> dateIntegrity(DataIntegrityExeption e, HttpServletRequest request) {

		StandartError err = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
