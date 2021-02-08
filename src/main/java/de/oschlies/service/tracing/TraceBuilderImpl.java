package de.oschlies.service.tracing;

import com.google.inject.Inject;
import de.oschlies.model.Microservice;
import de.oschlies.service.cmd.CommandLineReader;
import de.oschlies.service.file.FileReader;
import de.oschlies.service.tracing.hops.TraceHops;
import de.oschlies.service.tracing.hops.TraceHopsExactFactory;
import de.oschlies.service.tracing.hops.TraceHopsMaxFactory;
import de.oschlies.service.tracing.latency.TraceMaxLatency;
import de.oschlies.service.tracing.latency.TraceMaxLatencyFactory;
import de.oschlies.service.tracing.latency.TraceMaxLatencyImpl;
import de.oschlies.service.tracing.path.ShortestPath;
import de.oschlies.service.tracing.path.ShortestPathFactory;
import de.oschlies.service.tracing.route.RouteLatencyCalculator;
import de.oschlies.service.tracing.route.RouteLatencyFactory;
import java.util.LinkedList;
import java.util.List;

public class TraceBuilderImpl implements TraceBuilder {

  @Inject
  private CommandLineReader commandLineReader;
  @Inject
  private FileReader fileReader;
  @Inject
  private RouteLatencyFactory routeLatencyFactory;

  @Inject
  private ShortestPathFactory shortestPathFactory;

  @Inject
  private TraceHopsMaxFactory traceHopsMaxFactory;

  @Inject
  private TraceHopsExactFactory traceHopsExactFactory;

  @Inject
  private TraceMaxLatencyFactory traceMaxLatencyFactory;

  private List<Microservice> microservices;
  private RouteLatencyCalculator calculator;


  @Override
  public void createTrace(String[] args) {
    String fileLocation = commandLineReader.readFileLocation(args);
    this.microservices = fileReader.readTraceInputs(fileLocation);
    this.calculator = routeLatencyFactory.create(microservices);
    System.out.println("1. " + getABC());
    System.out.println("2. " + getAD());
    System.out.println("3. " + getADC());
    System.out.println("4. " + getAEBCD());
    System.out.println("4. " + getAED());
    TraceHops traceHopsMax = traceHopsMaxFactory.create(microservices);
    System.out.println("6. " + traceHopsMax.calculateHops("C", "C", 3));
    TraceHops traceHopsExact = traceHopsExactFactory.create(microservices);
    System.out.println("7. " + traceHopsExact.calculateHops("A", "C", 4));

    ShortestPath shortestPath = shortestPathFactory.create(microservices);
    System.out.println("8. " + shortestPath.calculateShortestPath("A", "C"));
    System.out.println("9. " + shortestPath.calculateShortestPath("B", "B"));

    TraceMaxLatency traceMaxLatency = traceMaxLatencyFactory.create(microservices);
    System.out.println("10. " + traceMaxLatency.calculateMaxLatency("C", "C", 30));
  }

  private String getABC() {
    LinkedList<String> route = new LinkedList<>();
    route.addFirst("A");
    route.addLast("B");
    route.addLast("C");
    return this.calculator.getRouteLatency(route);
  }

  private String getAD() {
    LinkedList<String> route = new LinkedList<>();
    route.addFirst("A");
    route.addLast("D");
    return calculator.getRouteLatency(route);
  }

  private String getADC() {
    LinkedList<String> route = new LinkedList<>();
    route.addFirst("A");
    route.addLast("D");
    route.addLast("C");
    return calculator.getRouteLatency(route);
  }

  private String getAEBCD() {
    LinkedList<String> route = new LinkedList<>();
    route.addFirst("A");
    route.addLast("E");
    route.addLast("B");
    route.addLast("C");
    route.addLast("D");
    return calculator.getRouteLatency(route);
  }

  private String getAED() {
    LinkedList<String> route = new LinkedList<>();
    route.addFirst("A");
    route.addLast("E");
    route.addLast("D");
    return calculator.getRouteLatency(route);
  }

}
