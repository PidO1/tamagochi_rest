package com.app.tamagotchi.reponse;

import lombok.Data;

import java.util.*;

@Data
public class ApiResult<T> implements Result<T> {

  private static final String SUCCESS_MESSAGE = "Successful";
  private List<ResultMessage> messages = new ArrayList();
  private T result;

  public ApiResult() {
  }

  public ApiResult<T> addMessage(final String message, final Severity severity) {
    return this.addMessages(Collections.singleton(new ResultMessage(message, severity)));
  }

  public ApiResult<T> addMessages(final Collection<ResultMessage> messages) {
    this.messages.addAll(messages);
    return this;
  }

  public ApiResult<T> withResult(final T result) {
    this.result = result;
    return this;
  }

  public ApiResult<T> withMessage(final String message, final Severity severity) {
    return this.withMessage(new ResultMessage(message, severity));
  }

  public ApiResult<T> withMessage(final ResultMessage message) {
    return this.withMessages(Arrays.asList(message));
  }

  public ApiResult<T> withMessages(final List<ResultMessage> messages) {
    this.messages = messages;
    return this;
  }
}
