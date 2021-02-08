package de.oschlies.service.tracing.path;

import de.oschlies.model.Microservice;
import java.util.List;

public interface ShortestPathFactory {

  ShortestPathImpl create(List<Microservice> microservices);

}
