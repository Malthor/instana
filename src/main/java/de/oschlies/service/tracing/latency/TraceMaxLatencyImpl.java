package de.oschlies.service.tracing.latency;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.oschlies.model.Connection;
import de.oschlies.model.Microservice;
import java.util.List;

public class TraceMaxLatencyImpl implements TraceMaxLatency {
  private List<Microservice> microservices;
  private int maxLatency;
  private int tracesFound = 0;

  @Inject
  public TraceMaxLatencyImpl(@Assisted List<Microservice> microservices){
    this.microservices = microservices;
  }

  @Override
  public int calculateMaxLatency(String start, String endpoint, int latency) {
    this.maxLatency = latency;
    for (Microservice microservice : microservices) {
      if (microservice.getName().equals(start)) {
        iterateEndpoints(microservice,endpoint, 0);
        break;
      }
    }
    return tracesFound;
  }
  private void iterateEndpoints(Microservice microservice,String endpoint, int currentLatency) {
    if (currentLatency <= maxLatency) {
      for (Connection connection : microservice.getConnections()) {
        for (Microservice destMicroService : microservices) {
          if (destMicroService.getName().equals(connection.getEndpoint())) {
            if (destMicroService.getName().equals(endpoint)) {
              this.tracesFound += 1;
            }
            currentLatency += connection.getLatency();
            iterateEndpoints(destMicroService,endpoint, currentLatency);
            break;
          }
        }
      }
    }
  }
}
