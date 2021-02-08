import de.oschlies.service.cmd.CommandLineReaderImpl
import de.oschlies.service.file.FileReaderImpl
import de.oschlies.service.tracing.TraceBuilder
import de.oschlies.service.tracing.TraceBuilderImpl
import de.oschlies.service.tracing.hops.*
import de.oschlies.service.tracing.latency.TraceMaxLatency
import de.oschlies.service.tracing.latency.TraceMaxLatencyFactory
import de.oschlies.service.tracing.latency.TraceMaxLatencyImpl
import de.oschlies.service.tracing.path.ShortestPath
import de.oschlies.service.tracing.path.ShortestPathFactory
import de.oschlies.service.tracing.path.ShortestPathImpl
import de.oschlies.service.tracing.route.RouteLatencyCalculator
import de.oschlies.service.tracing.route.RouteLatencyCalculatorImpl
import de.oschlies.service.tracing.route.RouteLatencyFactory
import spock.lang.Specification

class TraceBuilderSpec extends Specification {


  RouteLatencyFactory routeLatencyFactory = Mock()
  RouteLatencyCalculator routeLatency = Mock(RouteLatencyCalculatorImpl.class)

  ShortestPathFactory shortestPathFactory = Mock()
  ShortestPath shortestPath = Mock(ShortestPathImpl.class)

  TraceHopsMaxFactory traceHopsMaxFactory = Mock()
  TraceHops traceHopsMax = Mock(TraceHopsMax.class)

  TraceHopsExactFactory traceHopsExactFactory = Mock()
  TraceHops traceHopsExact = Mock(TraceHopsExact.class)

  TraceMaxLatencyFactory traceMaxLatencyFactory = Mock()
  TraceMaxLatency traceMaxLatency = Mock(TraceMaxLatencyImpl.class)

  TraceBuilder traceBuilder = new TraceBuilderImpl()


  def "createTrace"() {
    given:
    traceBuilder.commandLineReader = Mock(CommandLineReaderImpl.class)
    traceBuilder.fileReader = Mock(FileReaderImpl.class)
    traceBuilder.routeLatencyFactory = routeLatencyFactory
    routeLatencyFactory.create(null) >> routeLatency

    traceBuilder.shortestPathFactory = shortestPathFactory
    shortestPathFactory.create(null) >> shortestPath

    traceBuilder.traceHopsMaxFactory = traceHopsMaxFactory
    traceHopsMaxFactory.create(null) >> traceHopsMax

    traceBuilder.traceHopsExactFactory = traceHopsExactFactory
    traceHopsExactFactory.create(null) >> traceHopsExact

    traceBuilder.traceMaxLatencyFactory = traceMaxLatencyFactory
    traceMaxLatencyFactory.create(null) >> traceMaxLatency

    when:
    traceBuilder.createTrace(new String[0])
    then:
    5 * routeLatency.getRouteLatency(_ as LinkedList)
    2 * shortestPath.calculateShortestPath(_ as String, _ as String)
    1 * traceHopsMax.calculateHops(_ as String, _ as String, _ as Integer)
    1 * traceHopsExact.calculateHops(_ as String, _ as String, _ as Integer)
    1 * traceMaxLatency.calculateMaxLatency(_ as String, _ as String, _ as Integer)
  }
}