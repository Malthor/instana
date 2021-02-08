package de.oschlies.service.tracing;

import java.util.LinkedList;

public interface RouteLatencyCalculator {

  public String getRouteLatency(LinkedList<String> route);
}
