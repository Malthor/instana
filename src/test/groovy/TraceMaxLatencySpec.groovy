import de.oschlies.model.Connection
import de.oschlies.model.Microservice
import de.oschlies.service.tracing.latency.TraceMaxLatency
import de.oschlies.service.tracing.latency.TraceMaxLatencyImpl
import spock.lang.Specification

class TraceMaxLatencySpec extends Specification{

  def "calculateMaxLatency"(){
    given:
    TraceMaxLatency traceMaxLatency = new TraceMaxLatencyImpl(createMicroservices())
    when:
    def result = traceMaxLatency.calculateMaxLatency("A", "C", 40)
    then:
    result == 6
  }

  private static List<Microservice> createMicroservices() {
    Microservice microserviceA = new Microservice(
        name: "A",
        connections: [
            new Connection(
                endpoint: "B",
                latency: 2
            ),
            new Connection(
                endpoint: "C",
                latency: 6
            )
        ]
    )
    Microservice microserviceB = new Microservice(
        name: "B",
        connections: [
            new Connection(
                endpoint: "C",
                latency: 2
            )
        ]
    )
    Microservice microserviceC = new Microservice(
        name: "C",
        connections: [
            new Connection(
                endpoint: "B",
                latency: 12
            )
        ]
    )
    return List.of(microserviceA, microserviceB, microserviceC)
  }
}
