package com.app.tamagotchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.lang.Exception;
import io.sentry.Sentry;

@SpringBootApplication
@EnableTransactionManagement
public class TamagotchiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TamagotchiApplication.class, args);
 

  }

}
