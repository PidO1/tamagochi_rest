package com.app.tamagotchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.lang.Exception;
import io.sentry.Sentry;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class TamagotchiApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(TamagotchiApplication.class, args);
 

  }
}
