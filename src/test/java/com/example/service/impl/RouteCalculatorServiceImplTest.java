package com.example.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exception.CodeChallengeRuntimeException;
import com.example.model.RouteRequest;
import com.example.model.dijkstra.Graph;
import com.example.model.dijkstra.Node;
import com.example.service.DijkstraAlgorithmServiceI;
import com.example.utils.TestUtils;


@ExtendWith(MockitoExtension.class)
public class RouteCalculatorServiceImplTest {

  private static final List<String> TEST_CASES = Arrays.asList("case1", "case2");
  private static final String MAIN_FOLDER = "route-calculator-service";
  private static final String GRAPH_SUFFIX = "graph.yml";
  private static final String INPUT_SUFFIX = "input.yml";
  private static final String OUTPUT_SUFFIX = "output";

  @InjectMocks
  private RouteCalculatorServiceImpl routeCalculatorService;

  @Mock
  private DijkstraAlgorithmServiceI dijkstraAlgorithmService;

  @Test
  public void testCalculateBestRoute() throws Exception {
    for (String testCase : TEST_CASES) {
      RouteRequest routeRequest = TestUtils.convertFileToClass(
          TestUtils.getFilePath(MAIN_FOLDER, testCase, INPUT_SUFFIX),
          RouteRequest.class);
      Graph mockedGrapgh = TestUtils.convertFileToClass(
          TestUtils.getFilePath(MAIN_FOLDER, testCase, GRAPH_SUFFIX), Graph.class);
      String expectedResponse = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, OUTPUT_SUFFIX));
      Mockito.when(dijkstraAlgorithmService.calculateShortestPathFromSource(
          Mockito.any(Graph.class), Mockito.any(Node.class))).thenReturn(mockedGrapgh);
      String response = routeCalculatorService.calculateBestRoute(routeRequest);
      assertEquals(expectedResponse, response);
    }
  }

  @Test
  public void testFailConfigPath() throws Exception {
    Assertions.assertThrows(CodeChallengeRuntimeException.class,
        () -> routeCalculatorService.getConfiguredRoutes("ERROR_FILE"));
  }
}
