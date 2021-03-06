package com.example.model.dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.google.common.base.MoreObjects;

public class Node {
  
  private String name;
  
  private List<Node> shortestPath = new LinkedList<>();
  
  private Integer distance = Integer.MAX_VALUE;
  
  Map<Node, Integer> adjacentNodes = new HashMap<>();

  public void addDestination(Node destination, int distance) {
      adjacentNodes.put(destination, distance);
  }

  public Node() {
  }

  public Node(String name) {
      this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Node> getShortestPath() {
    return shortestPath;
  }

  public void setShortestPath(List<Node> shortestPath) {
    this.shortestPath = shortestPath;
  }

  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public Map<Node, Integer> getAdjacentNodes() {
    return adjacentNodes;
  }

  public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
    this.adjacentNodes = adjacentNodes;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Node) {
      Node that = (Node) object;
      return Objects.equals(this.name, that.name);
    }
    return false;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name).toString();
  }
}
