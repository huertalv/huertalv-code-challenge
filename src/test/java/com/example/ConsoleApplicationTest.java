package com.example;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.constant.MessageConstants;
import com.example.model.RouteRequest;
import com.example.service.RouteCalculatorServiceI;
import com.example.utils.TestUtils;

@SpringBootTest
public class ConsoleApplicationTest {

  private static final List<String> TEST_CASES = Arrays.asList("case1", "case2", "case3", "case4", "case5");
  private static final List<String> ORIGIN_FAIL_TEST_CASES = Arrays.asList("case-fail-origin");
  private static final List<String> DESTINATION_FAIL_TEST_CASES = Arrays.asList("case-fail-destination");
  private static final List<String> ROUTE_FAIL_TEST_CASES = Arrays.asList("case-fail-route");
  private static final String MAIN_FOLDER = "application";
  private static final String INPUT_FILE = "input";
  private static final String OUTPUT_FILE = "output";

  @Autowired  
  private RouteCalculatorServiceI routeCalculatorService;

  @Test
  public void runSuccessTest() throws Exception {
    for (String testCase : TEST_CASES) {
      String input = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, INPUT_FILE));
      String expectedOutput = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, OUTPUT_FILE));
      RouteRequest request = new RouteRequest();
      request.setOrigin(input.split("-")[0]);
      request.setDestination(input.split("-")[1]);
      String output = routeCalculatorService.calculateBestRoute(request);
      assertEquals("Expected output to be correct for case " + testCase,
          expectedOutput, output);
    }
  }

  @Test
  public void runFailureOriginNotFoundTest() throws Exception {
    for (String testCase : ORIGIN_FAIL_TEST_CASES) {
      String input = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, INPUT_FILE));
      RouteRequest request = new RouteRequest();
      request.setOrigin(input.split("-")[0]);
      request.setDestination(input.split("-")[1]);
      String output = routeCalculatorService.calculateBestRoute(request);
      assertEquals("Expected output to be correct for case " + testCase,
          MessageConstants.ORIGIN_NOT_FOUND, output);
    }
  }

  @Test
  public void runFailureDestinationNotFoundTest() throws Exception {
    for (String testCase : DESTINATION_FAIL_TEST_CASES) {
      String input = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, INPUT_FILE));
      RouteRequest request = new RouteRequest();
      request.setOrigin(input.split("-")[0]);
      request.setDestination(input.split("-")[1]);
      String output = routeCalculatorService.calculateBestRoute(request);
      assertEquals("Expected output to be correct for case " + testCase,
          MessageConstants.DESTINATION_NOT_FOUND, output);
    }
  }

  @Test
  public void runFailureRouteNotFoundTest() throws Exception {
    for (String testCase : ROUTE_FAIL_TEST_CASES) {
      String input = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, INPUT_FILE));
      RouteRequest request = new RouteRequest();
      request.setOrigin(input.split("-")[0]);
      request.setDestination(input.split("-")[1]);
      String output = routeCalculatorService.calculateBestRoute(request);
      assertEquals("Expected output to be correct for case " + testCase,
          MessageConstants.NO_ROUTE_FOUND, output);
    }
  }
}
