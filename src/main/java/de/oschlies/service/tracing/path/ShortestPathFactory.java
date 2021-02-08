package de.oschlies.service.tracing.path;

import de.oschlies.model.Microservice;
import de.oschlies.service.tracing.route.RouteLatencyCalculatorImpl;
import java.util.List;

public interface ShortestPathFactory {
  ShortestPathImpl create(List<Microservice> microservices);

}
