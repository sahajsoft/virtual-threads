package ai.sahaj.subscription.exception;

import ai.sahaj.subscription.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> exception(Exception e) {
    log.error("Global exception handler - custom exception", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler({PlanNotFoundException.class})
  public ResponseEntity<ErrorResponse> notFound(Exception e) {
    log.error("Global exception handler - custom exception", e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
  }
}
