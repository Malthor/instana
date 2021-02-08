package de.oschlies.service.file;

import de.oschlies.model.Microservice;
import java.util.List;

public interface FileReader {

  public List<Microservice> readTraceInputs(String fileLocation);
}
