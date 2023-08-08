package org.example.model.validator;

public enum ResponseStatus {
  CORRECT,
  EXCEPTION,
  ERROR;

  /**
   * method validate response
   *
   * @return response state
   */
  public static ResponseStatus get(String response) {
    if (response == null || response.isBlank()) {
      return ResponseStatus.EXCEPTION;
    } else if (response.contains("error")) {
      return ResponseStatus.ERROR;
    }
    return ResponseStatus.CORRECT;
  }
}
