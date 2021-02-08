package de.oschlies.service.tracing.hops;

import de.oschlies.model.Microservice;
import java.util.List;

public interface TraceHopsExactFactory {

  TraceHopsExact create(List<Microservice> microservices);
}
