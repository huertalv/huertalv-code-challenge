package com.example.constant;

public final class MessageConstants {
  private MessageConstants() {
    // Private constructor method to avoid calling the constructor from anywhere
  }

  public static final String ORIGIN_NOT_FOUND = "Departure airport wasn't found";
  public static final String DESTINATION_NOT_FOUND = "Arrival Airport not found for the departure specified";
  public static final String NO_ROUTE_FOUND = "There is no route between the nodes selected";

}
