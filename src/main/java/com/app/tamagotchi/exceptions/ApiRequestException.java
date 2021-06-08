package com.app.tamagotchi.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiRequestException extends RuntimeException {

  private final HttpStatus httpStatus;

  public ApiRequestException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public ApiRequestException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause);
    this.httpStatus = httpStatus;
  }
}
