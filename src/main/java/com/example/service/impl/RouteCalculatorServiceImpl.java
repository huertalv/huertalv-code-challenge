package com.example.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.constant.MessageConstants;
import com.example.exception.CodeChallengeRuntimeException;
import com.example.model.RouteNode;
import com.example.model.RouteRequest;
import com.example.model.RoutesConfig;
import com.example.model.dijkstra.Graph;
import com.example.model.dijkstra.Node;
import com.example.service.DijkstraAlgorithmServiceI;
import com.example.service.RouteCalculatorServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Service
public class RouteCalculatorServiceImpl implements RouteCalculatorServiceI {
  private static final String ROUTES_CONFIG_PATH = "routes.yml";
  @Autowired
  private DijkstraAlgorithmServiceI dijkstraAlgorithmService;

  @Override
  public String calculateBestRoute(RouteRequest routeRequest) {
    RoutesConfig routesConfig = getConfiguredRoutes(ROUTES_CONFIG_PATH);
    Map<String, Node> nodeMap = createNodeMap(routesConfig);
    Graph graph = createGraph(nodeMap);
    return getBestRoute(graph, nodeMap, routeRequest);
  }

  private String getBestRoute(Graph graph, Map<String, Node> nodeMap, RouteRequest routeRequest) {
    if (nodeMap.containsKey(routeRequest.getOrigin())) {
      graph = dijkstraAlgorithmService.calculateShortestPathFromSource(graph, nodeMap.get(routeRequest.getOrigin()));
      Node finalNode = graph.getNodes().stream()
        .filter(node -> node.getName().equals(routeRequest.getDestination()))
        .findFirst().orElse(null);
      if (Objects.isNull(finalNode)) {
        return MessageConstants.DESTINATION_NOT_FOUND;
      } else {
        return buildOutputString(finalNode);
      }
    } else {
      return MessageConstants.ORIGIN_NOT_FOUND;
    }
  }

  private String buildOutputString(Node finalNode) {
    StringBuilder builder = new StringBuilder();
    if (!finalNode.getShortestPath().isEmpty()) {
      Iterator<Node> iterator = finalNode.getShortestPath().iterator();
      Node source = iterator.next();
      Node destination;
      while (iterator.hasNext()) {
        destination = iterator.next();
        builder.append(getFormatedDistance(source, destination));
        source = destination;
      }
      destination = finalNode;
      builder.append(getFormatedDistance(source, destination));
      builder.append(String.format("time: %d", finalNode.getDistance()));
    }
    if (builder.length() == 0) {
      builder.append(MessageConstants.NO_ROUTE_FOUND);
    }
    return builder.toString();
  }

  private String getFormatedDistance(Node source, Node destination) {
    return String.format("%s -- %s ( %d )%n", source.getName(), destination.getName(),
        (destination.getDistance() - source.getDistance()));
  }

  Graph createGraph(Map<String, Node> nodeMap) {
    Graph graph = new Graph();
    nodeMap.values().forEach(graph::addNode);
    return graph;
  }

  Map<String, Node> createNodeMap(RoutesConfig routesConfig) {
    Map<String, Node> nodeMap = new HashMap<>();
    for (RouteNode routeNode : routesConfig.getRoutes()) {
      if (!nodeMap.containsKey(routeNode.getOrigin())) {
        nodeMap.put(routeNode.getOrigin(), new Node(routeNode.getOrigin()));
      }
      if (!nodeMap.containsKey(routeNode.getDestination())) {
        nodeMap.put(routeNode.getDestination(), new Node(routeNode.getDestination()));
      }
      nodeMap.get(routeNode.getOrigin()).addDestination(
          nodeMap.get(routeNode.getDestination()), routeNode.getDuration());
    }
    return nodeMap;
  }

  RoutesConfig getConfiguredRoutes(String routesConfigPath) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    InputStream routesInputStream = this.getClass().getClassLoader().getResourceAsStream(routesConfigPath);
    try {
      return mapper.readValue(routesInputStream, RoutesConfig.class);
    } catch (Exception exception) {
      throw new CodeChallengeRuntimeException("Error parsing config file", exception);
    }
  }
}
