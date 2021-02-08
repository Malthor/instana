import de.oschlies.service.cmd.CommandLineReader
import de.oschlies.service.cmd.CommandLineReaderImpl
import spock.lang.Specification

class CommandLineReaderSpec extends Specification{

  CommandLineReader commandLineReader = new CommandLineReaderImpl();

  def "readFileLocation file missing"(){
    when:
    String result = commandLineReader.readFileLocation(new String[0])
    then:
    result.isEmpty()
  }

  def "readFileLocation"(){
    given:
    String[] args = ["--file=test"]
    when:
    String result = commandLineReader.readFileLocation(args)
    then:
    result == "test"
  }
}
