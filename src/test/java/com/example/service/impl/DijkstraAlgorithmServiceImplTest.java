package com.example.service.impl;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.RoutesConfig;
import com.example.model.dijkstra.Graph;
import com.example.model.dijkstra.Node;
import com.example.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class DijkstraAlgorithmServiceImplTest {
  private static final List<String> TEST_CASES = Arrays.asList("case1", "case2");
  private static final String MAIN_FOLDER = "dijkstra-algorithm-service";
  private static final String NODE_FILE = "node";
  private static final String OUTPUT_GRAPH_FILE = "output-graph.yml";

  @InjectMocks
  private DijkstraAlgorithmServiceImpl dijkstraAlgorithmService;

  private RouteCalculatorServiceImpl routeCalculatorServiceImpl = new RouteCalculatorServiceImpl();

  @Test
  public void calculateShortestPathFromSourceTest() throws Exception {
    for (String testCase : TEST_CASES) {
      RoutesConfig routesConfig = routeCalculatorServiceImpl.getConfiguredRoutes("routes.yml");
      Map<String, Node> nodeMap = routeCalculatorServiceImpl.createNodeMap(routesConfig);
      Graph graph = routeCalculatorServiceImpl.createGraph(nodeMap);
      String nodeName = TestUtils.asString(TestUtils.getFilePath(MAIN_FOLDER, testCase, NODE_FILE));
      Graph expectedOutputGraph = TestUtils.convertFileToClass(
          TestUtils.getFilePath(MAIN_FOLDER, testCase, OUTPUT_GRAPH_FILE), Graph.class);
      Graph outputGraph = dijkstraAlgorithmService.calculateShortestPathFromSource(graph, nodeMap.get(nodeName));
      Node calculatedNodePath = outputGraph.getNodes().stream()
          .filter(node -> node.getName().equals(nodeName))
          .findFirst().orElse(null);
      if (Objects.nonNull(calculatedNodePath)) {
        for (Node outputNode : calculatedNodePath.getShortestPath()) {
          Node foundNode = expectedOutputGraph.getNodes().stream()
            .filter(expectedOutputNode -> outputNode.getName().equals(expectedOutputNode.getName()))
            .findFirst().orElse(null);
          if (Objects.isNull(foundNode)) {
            fail(String.format("The node %s wasn't found on the resultant graph", outputNode.getName()));
          } else {
            assertEquals(foundNode.getDistance(), outputNode.getDistance(),
                "The distance of the node is not the expected one");
          }
        }
      } else {
        fail("The source node wasn't found on the graph");
      }
    }
  }
}
