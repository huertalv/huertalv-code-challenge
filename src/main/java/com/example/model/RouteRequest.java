package com.example.model;

import java.util.Objects;
import com.google.common.base.MoreObjects;

public class RouteRequest {

  private String origin;
  private String destination;

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  @Override
  public int hashCode() {
    return Objects.hash(origin, destination);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof RouteRequest) {
      RouteRequest that = (RouteRequest) object;
      return Objects.equals(this.origin, that.origin) && Objects.equals(this.destination, that.destination);
    }
    return false;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("origin", origin).add("destination", destination).toString();
  }
}
