import de.oschlies.model.Connection
import de.oschlies.model.Microservice
import de.oschlies.service.tracing.hops.TraceHops
import de.oschlies.service.tracing.hops.TraceHopsExact
import de.oschlies.service.tracing.hops.TraceHopsMax
import spock.lang.Specification

class TraceHopsSpec extends Specification{

  def "calculateHops exact"(){
    given:
    TraceHops traceHops = new TraceHopsExact(createMicroservices())
    when:
    def result = traceHops.calculateHops("A", "C" , 2)
    then:
    result == 1
  }

  def "calculateHops max"(){
    given:
    TraceHops traceHops = new TraceHopsMax(createMicroservices())
    when:
    def result = traceHops.calculateHops("A", "C" , 2)
    then:
    result == 2
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
