package com.example.exception;

public class CodeChallengeRuntimeException extends RuntimeException {

  private static final long serialVersionUID = -5725545587481851928L;

  public CodeChallengeRuntimeException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
