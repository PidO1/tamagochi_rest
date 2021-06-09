package com.app.tamagotchi;

import com.app.tamagotchi.requests.users.UsersDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TamagotchiApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(TamagotchiApplication.class, args);
  }
}
