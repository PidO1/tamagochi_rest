package com.app.tamagotchi.exceptions;


import com.app.tamagotchi.utils.GenericUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExpetionHandler {

  @ExceptionHandler(value = {ApiRequestException.class})
  public ResponseEntity<Object> handleException(ApiRequestException apiRequestException){
    ApiException apiException = new ApiException(apiRequestException.getMessage(), apiRequestException.getHttpStatus(),
        GenericUtility.getSynchronisedDate());
      return new ResponseEntity<>(apiException, apiRequestException.getHttpStatus());
  }

}
