package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.model.RouteRequest;
import com.example.service.RouteCalculatorServiceI;

@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {
  private static final Logger LOG = LoggerFactory.getLogger(ConsoleApplication.class);

  @Autowired
  private RouteCalculatorServiceI routeCalculatorService;

  public static void main(String[] args) {
    SpringApplication.run(ConsoleApplication.class, args);
  }

  public void run(String... args) throws Exception {
    RouteRequest request = new RouteRequest();
    if (args.length > 0) {
      request.setOrigin(args[0].split("-")[0]);
      request.setDestination(args[0].split("-")[1]);
      LOG.info("\n{}", routeCalculatorService.calculateBestRoute(request));
    }
  }

}
