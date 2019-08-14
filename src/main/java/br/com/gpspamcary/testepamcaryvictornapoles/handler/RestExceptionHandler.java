package br.com.gpspamcary.testepamcaryvictornapoles.handler;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.gpspamcary.testepamcaryvictornapoles.handler.dto.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	protected ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Registro inexistente.", ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleEmptyResultDataAccess(ConstraintViolationException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, getMessageConstraintViolations(ex.getConstraintViolations()), ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	protected ResponseEntity<Object> handleEmptyResultDataAccess(TransactionSystemException ex) {
		
		Throwable rolbackException = ex.getCause();
		Throwable constraintViolation = rolbackException.getCause();
		
		String message = constraintViolation.getMessage();
		
		if(constraintViolation instanceof ConstraintViolationException) {
			message = getMessageConstraintViolations(((ConstraintViolationException)constraintViolation).getConstraintViolations());
		}
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, rolbackException);
		return buildResponseEntity(apiError);
	}
	
	
	private String getMessageConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
		
		Optional<Set<ConstraintViolation<?>>> optConstraintViolations = Optional.ofNullable(constraintViolations);
		
		return optConstraintViolations.map(constraintViolation ->{
			StringBuilder builder = new StringBuilder();
			
			builder.append("Atributos obrigatórios:");
			
			constraintViolation.forEach(c ->{
				builder.append(" ");
				builder.append(c.getPropertyPath());
			});
			
			return builder.toString();
		}).orElse("");
		
		 
	}
	

	
	

}
