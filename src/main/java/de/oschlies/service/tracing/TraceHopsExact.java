package de.oschlies.service.tracing;

import de.oschlies.model.Connection;
import de.oschlies.model.Microservice;
import java.util.List;

public class TraceHopsExact implements TraceHops{
  private List<Microservice> microservices;

  public TraceHopsExact(List<Microservice> microservices){
    this.microservices = microservices;
  }

  @Override
  public int calculateHops(String start, String endpoint, int hops) {
    int result = 0;
    int iterations = 1;
    for (Microservice microservice : microservices) {
      if (microservice.getName().equals(start)) {
        result = iterateMicroservices(microservice,endpoint, hops, iterations);
        break;
      }
    }
    return result;
  }
  private int iterateMicroservices(Microservice microservice,String endpoint, int maxHops, int iterations) {
    int result = 0;
    if (iterations <= maxHops) {
      for (Connection connection : microservice.getConnections()) {
        result = iterateConnection(endpoint, maxHops, iterations, result, connection);
      }
    }
    return result;
  }

  private int iterateConnection(String endpoint, int maxHops, int iterations, int result,
      Connection connection) {
    int pathIterations = iterations;
    for (Microservice destMicroService : microservices) {
      if (destMicroService.getName().equals(connection.getEndpoint())) {
        if (destMicroService.getName().equals(endpoint) && pathIterations == maxHops) {
          result += 1;
        }
        pathIterations += 1;
        result += iterateMicroservices(destMicroService, endpoint, maxHops, pathIterations);
        break;
      }
    }
    return result;
  }
}
