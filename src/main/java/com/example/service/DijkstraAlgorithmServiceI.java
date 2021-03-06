package com.example.service;

import com.example.model.dijkstra.Graph;
import com.example.model.dijkstra.Node;

public interface DijkstraAlgorithmServiceI {
  Graph calculateShortestPathFromSource(Graph graph, Node source);
}
