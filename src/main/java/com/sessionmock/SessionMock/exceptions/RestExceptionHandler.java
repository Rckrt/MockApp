package com.sessionmock.SessionMock.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all types of exceptions which may occurs on REST call and returns appropriate status
 * code and message
 */
@ControllerAdvice(annotations = RestController.class, basePackages = "com.sessionmock.SessionMock")
public class RestExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException exception) {
    return createExceptionDTO(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = InvalidScriptParameters.class)
  public ResponseEntity<Map<String, Object>> handleInvalidScriptParametersException(
          InvalidScriptParameters exception) {
    return createExceptionDTO(exception, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = PatternValidationException.class)
  public ResponseEntity<Map<String, Object>> handlePatternValidationException(
          PatternValidationException exception) {
    return createExceptionDTO(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = PreviousRequestNotExist.class)
  public ResponseEntity<Map<String, Object>> handlePreviousRequestNotExist(
          PreviousRequestNotExist exception) {
    return createExceptionDTO(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = UrlNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUrlNotFoundException(
          UrlNotFoundException exception) {
    return createExceptionDTO(exception, HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<Map<String, Object>> createExceptionDTO(Exception ex,
                                                                 HttpStatus httpStatus) {
    LOG.trace("Handle exception", ex);
    Map<String, Object> exceptionDTO = new HashMap<>();
    exceptionDTO.put("message",
        StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : "Unknown Error");
    exceptionDTO.put("errorType", ex.getClass().getSimpleName());
    if (ex.getCause() != null) {
      exceptionDTO.put("cause",
          StringUtils.isNotEmpty(ex.getCause().getMessage()) ? ex.getCause().getMessage()
              : "Unknown Cause");
    }

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(exceptionDTO, httpHeaders, httpStatus);
  }

}
