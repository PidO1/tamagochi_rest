package com.app.tamagotchi.enums;

public enum NextStep {
  LOGIN("LOGIN"),
  CREATE_PET("CREATE_PET"),
  CUSTOMIZE_PET("CUSTOMIZE_PET"),
  REDO("REDO_STEP");

  private String nextStep;

  NextStep(String nextStep) {
    this.nextStep = nextStep;
  }

  public String getNextStep() {
    return nextStep;
  }
}
