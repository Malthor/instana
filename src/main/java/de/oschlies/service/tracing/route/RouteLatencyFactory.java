package de.oschlies.service.tracing.route;

import de.oschlies.model.Microservice;
import java.util.List;

public interface RouteLatencyFactory {

  RouteLatencyCalculatorImpl create(List<Microservice> microservices);
}
