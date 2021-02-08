package de.oschlies.service.tracing;

import de.oschlies.model.Connection;
import de.oschlies.model.Microservice;
import java.util.LinkedList;
import java.util.List;

public class RouteLatencyCalculatorImpl implements RouteLatencyCalculator{
  private final List<Microservice> microservices;

  public RouteLatencyCalculatorImpl(List<Microservice> microservices){
    this.microservices = microservices;
  }

  @Override
  public String getRouteLatency(LinkedList<String> route) {
    var it = route.iterator();
    int location = 1;
    int latency = 0;
    String result = "";
    while (it.hasNext()) {
      String start = it.next();
      if (it.hasNext()) {
        String dest = route.get(location);
        int resultLat = getLatencyBetweenServices(start, dest);
        if (resultLat > 0) {
          latency += resultLat;
        } else {
          latency = 0;
          break;
        }
      }
      location++;
    }
    if (latency <= 0) {
      result = "NO SUCH TRACE";
    } else {
      result = String.valueOf(latency);
    }
    return result;
  }

  private int getLatencyBetweenServices(String start, String destination) {
    int latency = 0;
    for (Microservice microservice : microservices) {
      if (microservice.getName().equals(start)) {
        for (Connection connection : microservice.getConnections()) {
          if (connection.getEndpoint().equals(destination)) {
            latency += connection.getLatency();
            break;
          }
        }
        break;
      }
    }
    return latency;
  }
}
