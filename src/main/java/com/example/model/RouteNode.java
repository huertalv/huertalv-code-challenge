package com.example.model;

import java.util.Objects;
import com.google.common.base.MoreObjects;

public class RouteNode {

  private String origin;
  private String destination;
  private int duration;

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

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public int hashCode() {
    return Objects.hash(origin, destination, duration);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof RouteNode) {
      RouteNode that = (RouteNode) object;
      return Objects.equals(this.origin, that.origin) && Objects.equals(this.destination, that.destination)
          && this.duration == that.duration;
    }
    return false;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("origin", origin).add("destination", destination)
        .add("duration", duration).toString();
  }
}
