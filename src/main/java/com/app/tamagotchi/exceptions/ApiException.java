package com.app.tamagotchi.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ApiException {

  private final String message;
  private final HttpStatus httpStatus;
  private final Date timeStamp;

  public ApiException(String message, HttpStatus httpStatus, Date timeStamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timeStamp = timeStamp;
  }


}