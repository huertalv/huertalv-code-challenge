package com.example.service;

import com.example.model.RouteRequest;

public interface RouteCalculatorServiceI {
  String calculateBestRoute(RouteRequest routeRequest);
}
