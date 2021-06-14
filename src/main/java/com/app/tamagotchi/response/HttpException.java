package com.app.tamagotchi.response;


import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class HttpException extends Exception 
{

  // This class defines a HTTP error with a message

  private HttpStatus httpStatus;
  private String errorMessage;
  private Exception exception;
  

  public HttpException(HttpStatus httpStatus, String errorMessage)
  {
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
  }

  public HttpException(HttpStatus httpStatus, String errorMessage, Exception exception)
  {
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
    this.exception = exception;
  }
}
