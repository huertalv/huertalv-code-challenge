package com.example.model;

import java.util.List;
import java.util.Objects;
import com.google.common.base.MoreObjects;

public class RoutesConfig {
  private List<RouteNode> routes;

  public List<RouteNode> getRoutes() {
    return routes;
  }

  public void setRoutes(List<RouteNode> routes) {
    this.routes = routes;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(routes);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof RoutesConfig) {
      RoutesConfig that = (RoutesConfig) object;
      return Objects.equals(this.routes, that.routes);
    }
    return false;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("routes", routes).toString();
  }
}
