import de.oschlies.model.Connection
import de.oschlies.model.Microservice
import de.oschlies.service.tracing.route.RouteLatencyCalculator
import de.oschlies.service.tracing.route.RouteLatencyCalculatorImpl
import spock.lang.Specification

class RouteLatencyCalculatorSpec extends Specification{

  def "getRouteLatency"(){
    given:
    RouteLatencyCalculator routeLatencyCalculator = new RouteLatencyCalculatorImpl(createMicroservices())
    LinkedList<String> route = new LinkedList<>()
    route.add("A")
    route.addLast("B")
    when:
    def result = routeLatencyCalculator.getRouteLatency(route)
    then:
    result == "2"
  }

  def "getRouteLatency: No Such Trace"(){
    given:
    RouteLatencyCalculator routeLatencyCalculator = new RouteLatencyCalculatorImpl(createMicroservices())
    LinkedList<String> route = new LinkedList<>()
    route.add("A")
    route.addLast("C")
    when:
    def result = routeLatencyCalculator.getRouteLatency(route)
    then:
    result == "NO SUCH TRACE"
  }

  private static List<Microservice> createMicroservices(){
    Microservice microserviceA = new Microservice(
        name: "A",
        connections: [
            new Connection(
                endpoint: "B",
                latency: 2
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
    return List.of(microserviceA, microserviceB)
  }
}
