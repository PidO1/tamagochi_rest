package com.app.tamagotchi.response;

import lombok.Data;

@Data
public class ResultMessage {

  private String message;

  private Severity severity;

  public ResultMessage() {
  }

  public ResultMessage withMessage(final String message) {
    this.message = message;
    return this;
  }

  public ResultMessage withMessageSeverity(final Severity severity) {
    this.severity = severity;
    return this;
  }

  public static ResultMessage.ResultMessageBuilder builder() {
    return new ResultMessage.ResultMessageBuilder();
  }

  public ResultMessage(final String message, final Severity severity) {
    this.message = message;
    this.severity = severity;
  }

  public static class ResultMessageBuilder {
    private String message;
    private Severity severity;

    ResultMessageBuilder() {}

    public ResultMessage.ResultMessageBuilder message(final String message) {
      this.message = message;
      return this;
    }

    public ResultMessage.ResultMessageBuilder severity(final Severity severity) {
      this.severity = severity;
      return this;
    }

    public ResultMessage build() {
      return new ResultMessage(this.message, this.severity);
    }

    public String toString() {
      return "ResultMessage.ResultMessageBuilder(message=" + this.message + ", severity=" + this.severity + ")";
    }
  }
}
