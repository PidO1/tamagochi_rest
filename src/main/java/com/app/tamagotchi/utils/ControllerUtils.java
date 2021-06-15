package com.app.tamagotchi.utils;


import com.app.tamagotchi.enums.Severity;
import com.app.tamagotchi.response.ApiResult;
import com.app.tamagotchi.response.ResultMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ControllerUtils {
  private static final int ERROR_CODE_THRESHOLD = 400;

  private ControllerUtils() {
  }

  public static <T> ResponseEntity<ApiResult<T>> responseOf(HttpStatus status, String message) {
    return responseOf(status, null, message, null);
  }

  public static <T> ResponseEntity<ApiResult<T>> responseOf(HttpStatus status, String message, String nextStep) {
    return responseOf(status, null, message, nextStep);
  }

  public static <T> ResponseEntity<ApiResult<T>> responseOf(HttpStatus status, T result, String message) {
    return responseOf(status, result, message, null);
  }

  public static <T> ResponseEntity<ApiResult<T>> responseOf(HttpStatus status, T result, String message, String nextStep) {
    return ResponseEntity.status(status)
            .body(new ApiResult<T>()
                    .withMessage(ResultMessage.builder()
                            .severity(status.value() >= ERROR_CODE_THRESHOLD ? Severity.ERROR : Severity.INFO)
                            .message(message)
                            .build())
                    .withResult(result)
                    .nextStep(nextStep));

  }
}
