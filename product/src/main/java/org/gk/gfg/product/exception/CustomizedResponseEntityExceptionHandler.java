package org.gk.gfg.product.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex,
      WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        new ArrayList<>(Arrays.asList(new String[] {request.getDescription(false)})));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ProductServiceException.class)
  public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(
      ProductServiceException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ExceptionResponse error = new ExceptionResponse(new Date(), "Record Not Found", details);
    return new ResponseEntity(error, HttpStatus.NOT_FOUND);
  }

}

