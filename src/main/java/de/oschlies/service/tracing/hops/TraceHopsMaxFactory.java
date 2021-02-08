package de.oschlies.service.tracing.hops;

import de.oschlies.model.Microservice;
import java.util.List;

public interface TraceHopsMaxFactory {

  TraceHopsMax create(List<Microservice> microservices);

}
