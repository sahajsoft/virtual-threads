package ai.sahaj.subscription.exception;

public class PlanNotFoundException extends RuntimeException{
  public PlanNotFoundException(String message) {
    super(message);
  }
}
