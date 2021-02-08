package de.oschlies.service.file;

import de.oschlies.model.Connection;
import de.oschlies.model.Microservice;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileReaderImpl implements FileReader {

  /*
  Microservice has to has to start with two letter and can have a
   */
  private final String MICROSERVICE_DEFINITION_PATTERN = "^[A-Za-z]{2}[0-9]+$";

  @Override
  public List<Microservice> readTraceInputs(String fileLocation) {
    Map<String, Microservice> microserviceMap = new HashMap<>();
    try {
      Scanner scanner = new Scanner(new File(fileLocation));
      scanner.useDelimiter(",");
      while (scanner.hasNext()) {
        Microservice microservice = createMicroservice(scanner.next());
        if (microserviceMap.containsKey(microservice.getName())) {
          var alreadyUserMicro = microserviceMap.get(microservice.getName());
          alreadyUserMicro.getConnections().addAll(microservice.getConnections());
        } else {
          microserviceMap.put(microservice.getName(), microservice);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Can not find a file under the location: " + fileLocation);
    }
    return new ArrayList<>(microserviceMap.values());
  }

  protected Microservice createMicroservice(String definition) {
    Microservice microservice;
    String trimmed = definition.trim();
    if (trimmed.matches(MICROSERVICE_DEFINITION_PATTERN)) {
      microservice = new Microservice();
      microservice.setName(trimmed.substring(0, 1));
      Connection connection = new Connection();
      connection.setEndpoint(trimmed.substring(1, 2));
      connection.setLatency(Integer.parseInt(trimmed.substring(2)));
      microservice.getConnections().add(connection);
    } else {
      throw new RuntimeException("The microservice pattern is not supported: " + definition);
    }
    return microservice;
  }
}
