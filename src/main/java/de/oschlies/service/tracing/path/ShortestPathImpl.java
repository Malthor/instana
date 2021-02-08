package de.oschlies.service.tracing.path;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.oschlies.model.Connection;
import de.oschlies.model.Microservice;
import java.util.ArrayList;
import java.util.List;

public class ShortestPathImpl implements ShortestPath {

  private List<Microservice> microservices;
  private List<Microservice> visitedMicroservices;
  private int currentFastest = 0;

  @Inject
  public ShortestPathImpl(@Assisted List<Microservice> microservices) {
    this.microservices = microservices;
  }

  public int calculateShortestPath(String start, String endpoint) {
    this.currentFastest = 0;
    this.visitedMicroservices = new ArrayList<>();
    for (Microservice microservice : microservices) {
      if (microservice.getName().equals(start)) {
        iterateEndpoints(microservice, endpoint, 0);
        break;
      }
    }
    return currentFastest;
  }

  private void iterateEndpoints(Microservice microservice, String endpoint, int latency) {
    for (Connection connection : microservice.getConnections()) {
      for (Microservice destMicroService : microservices) {
        int pathLatency = latency;
        if (destMicroService.getName().equals(connection.getEndpoint()) && !visitedMicroservices
            .contains(destMicroService)) {
          pathLatency += connection.getLatency();
          visitedMicroservices.add(destMicroService);
          if (destMicroService.getName().equals(endpoint)) {
            updateCurrentFastest(pathLatency);
            break;
          }
          iterateEndpoints(destMicroService, endpoint, pathLatency);
        }
      }
      this.visitedMicroservices = new ArrayList<>();
    }
  }

  private void updateCurrentFastest(int pathLatency) {
    if (pathLatency < currentFastest || currentFastest == 0) {
      currentFastest = pathLatency;
    }
  }

}
