package com.app.tamagotchi.utils;


import com.app.tamagotchi.response.ApiResult;
import com.app.tamagotchi.response.ResultMessage;
import com.app.tamagotchi.response.Severity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ControllerUtils {
  private static final int ERROR_CODE_THRESHOLD = 400;

  private ControllerUtils() {
  }

  public static <T> ResponseEntity<ApiResult<T>> responseOf(HttpStatus status, String message) {
    return responseOf(status, null, message);
  }

  public static <T> ResponseEntity<ApiResult<T>> responseOf(HttpStatus status, T result, String message) {
    return ResponseEntity.status(status)
            .body(new ApiResult<T>()
                    .withMessage(ResultMessage.builder()
                            .severity(status.value() >= ERROR_CODE_THRESHOLD ? Severity.ERROR : Severity.INFO)
                            .message(message)
                            .build())
                    .withResult(result));

  }

}
