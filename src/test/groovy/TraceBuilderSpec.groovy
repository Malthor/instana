import de.oschlies.service.cmd.CommandLineReader
import de.oschlies.service.cmd.CommandLineReaderImpl
import de.oschlies.service.file.FileReaderImpl
import de.oschlies.service.tracing.RouteLatencyCalculator
import de.oschlies.service.tracing.RouteLatencyCalculatorImpl
import de.oschlies.service.tracing.TraceBuilder
import de.oschlies.service.tracing.TraceBuilderImpl
import spock.lang.Specification

class TraceBuilderSpec extends Specification{

  CommandLineReaderImpl commandLineReader = Mock()
  FileReaderImpl fileReader = Mock()
  TraceBuilder traceBuilder = new TraceBuilderImpl(commandLineReader, fileReader)

  def "createTrace"(){
    given:
    def  eventPublisher = Spy(RouteLatencyCalculatorImpl)

    when:
    traceBuilder.createTrace(new String[0])
    then:
    1 * eventPublisher.getRouteLatency(_ as LinkedList)
  }
}
