package com.malds.groceriesProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class GroceriesProjectApplication {

  /**
   * test method.
   * @return hello world
   */
  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Hello World!";
  }

  /**
   * main application.
   * @param args
   */
  public static void main(final String[] args) {
    SpringApplication.run(GroceriesProjectApplication.class, args);
  }
}
