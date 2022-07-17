package br.com.firedev.util;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerAdvice.class);

	@ExceptionHandler({ RuleException.class })
	public final ResponseEntity<Object> unknownException(RuleException ex, WebRequest request) {
		var message = ex.getMessage();
		return this.handleCustomException(message, ex, request);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public final ResponseEntity<Object> unknownException(ConstraintViolationException ex, WebRequest request) {
		var message = ex.getMessage();
		return this.handleCustomException(message, ex, request);
	}

	@ExceptionHandler({ InvalidDataAccessResourceUsageException.class, DataIntegrityViolationException.class })
	public final ResponseEntity<Object> unknownException(NonTransientDataAccessException ex, WebRequest request) {
		var message = ex.getCause().getCause().getMessage();
		return this.handleCustomException(message, ex, request);
	}

	private final ResponseEntity<Object> handleCustomException(String message, Exception ex, WebRequest request) {
		var stacktrace = Arrays.toString(ex.getStackTrace());
		LOG.warn(message);
		LOG.warn(stacktrace);
		return this.handleExceptionInternal(ex, ex, new HttpHeaders(),
				HttpStatus.EXPECTATION_FAILED, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, ex.getBindingResult(), headers, status, request);
	}
}
