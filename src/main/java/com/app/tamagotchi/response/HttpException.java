package com.app.tamagotchi.response;


import org.springframework.http.HttpStatus;
import lombok.Data;


@Data
public class HttpException extends Exception 
{

  // This class defines a HTTP error with a message

  private HttpStatus httpStatus;
  private String errorMessage;
  

  public HttpException(HttpStatus httpStatus, String errorMessage)
  {
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
  }
}
