package com.standardbank.quantumleap.security.services.authenticators.models;

public enum Challenge {
  NEW_PASSWORD_REQUIRED("UPDATE_PASSWORD"),
  SMS_MFA("OTP"),
  LOGIN("LOGIN"),
  FORGOT_PASSWORD_NEW("NEW_PASSWORD");

  private String nextStep;

  Challenge(String nextStep) {
    this.nextStep = nextStep;
  }

  public String getNextStep() {
    return nextStep;
  }
}
