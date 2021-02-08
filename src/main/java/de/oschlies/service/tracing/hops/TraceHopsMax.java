package de.oschlies.service.tracing.hops;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.oschlies.model.Connection;
import de.oschlies.model.Microservice;
import java.util.List;

public class TraceHopsMax implements TraceHops {

  private List<Microservice> microservices;

  @Inject
  public TraceHopsMax(@Assisted List<Microservice> microservices) {
    this.microservices = microservices;
  }

  @Override
  public int calculateHops(String start, String endpoint, int hops) {
    int result = 0;
    int iterations = 1;
    for (Microservice microservice : microservices) {
      if (microservice.getName().equals(start)) {
        result = iterateEndpoints(microservice, endpoint, hops, iterations);
        break;
      }
    }
    return result;
  }

  private int iterateEndpoints(Microservice microservice, String endpoint, int maxHops,
      int iterations) {
    int result = 0;
    if (iterations <= maxHops) {
      for (Connection connection : microservice.getConnections()) {
        int pathIterations = iterations;
        for (Microservice destMicroService : microservices) {
          if (destMicroService.getName().equals(connection.getEndpoint())) {
            pathIterations += 1;
            if (destMicroService.getName().equals(endpoint)) {
              result += 1;
            }
            result += iterateEndpoints(destMicroService, endpoint, maxHops, pathIterations);
            break;
          }
        }
      }
    }
    return result;
  }
}
