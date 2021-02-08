package de.oschlies.service.tracing.latency;

import de.oschlies.model.Microservice;
import java.util.List;

public interface TraceMaxLatencyFactory {

  TraceMaxLatencyImpl create(List<Microservice> microservices);
}
