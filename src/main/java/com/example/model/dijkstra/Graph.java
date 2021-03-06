package com.example.model.dijkstra;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.google.common.base.MoreObjects;

public class Graph {
  private Set<Node> nodes = new HashSet<>();
  
  public void addNode(Node nodeA) {
      nodes.add(nodeA);
  }

  public Set<Node> getNodes() {
    return nodes;
  }

  public void setNodes(Set<Node> nodes) {
    this.nodes = nodes;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(nodes);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Graph) {
      Graph that = (Graph) object;
      return Objects.equals(this.nodes, that.nodes);
    }
    return false;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("nodes", nodes).toString();
  }
}
