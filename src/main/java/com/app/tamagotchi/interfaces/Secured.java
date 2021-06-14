package com.app.tamagotchi.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {
  SecureStatus secureStatus() default SecureStatus.PRIVATE;

  String[] roles() default "";

  enum SecureStatus {
    PUBLIC,
    PRIVATE
  }
}
