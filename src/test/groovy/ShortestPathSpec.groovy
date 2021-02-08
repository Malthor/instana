import de.oschlies.model.Connection
import de.oschlies.model.Microservice
import de.oschlies.service.tracing.ShortestPath
import de.oschlies.service.tracing.ShortestPathImpl
import spock.lang.Specification

class ShortestPathSpec extends Specification {

  def "getRouteLatency"() {
    given:
    ShortestPath shortestPath = new ShortestPathImpl(createMicroservices())
    when:
    def result = shortestPath.calculateShortestPath("A", "C")
    then:
    result == 4
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
