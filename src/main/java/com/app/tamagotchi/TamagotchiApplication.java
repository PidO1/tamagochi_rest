package com.app.tamagotchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.lang.Exception;
import io.sentry.Sentry;
import io.sentry.spring.EnableSentry;
// NOTE: Replace the test DSN below with YOUR OWN DSN to see the events from this app in your Sentry
// project/dashboard
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSentry(dsn = "https://dddd694ff5c94e13b686e50ba44f5bc0@o842130.ingest.sentry.io/5813958") // https://dddd694ff5c94e13b686e50ba44f5bc0@o842130.ingest.sentry.io/5813958
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableSwagger2
public class TamagotchiApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {

    try {
      SpringApplication.run(TamagotchiApplication.class, args);

    } catch (Exception e) {
      Sentry.captureException(e);
    }

  }
}
