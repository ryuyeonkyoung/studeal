package com.studeal.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StuDealApplication {

  public static void main(String[] args) {
    SpringApplication.run(StuDealApplication.class, args);
  }

}
