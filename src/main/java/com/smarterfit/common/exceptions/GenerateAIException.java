package com.smarterfit.common.exceptions;

public class GenerateAIException extends RuntimeException {
  public GenerateAIException(String message) {
    super(message);
  }

  public GenerateAIException(String message, Throwable cause) {
      super(message, cause);
  }
}
