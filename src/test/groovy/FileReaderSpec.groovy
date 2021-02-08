import de.oschlies.service.file.FileReader
import de.oschlies.service.file.FileReaderImpl
import spock.lang.Specification

class FileReaderSpec extends Specification {

  FileReader fileReader = new FileReaderImpl()

  def "readTraceInputs FileNotFoundException"() {
    when:
    fileReader.readTraceInputs("/lost")
    then:
    RuntimeException exception = thrown()
    exception.getMessage() == "Can not find a file under the location: /lost"
  }

  def "createMicroservice Pattern not supported"() {
    when:
    fileReader.createMicroservice("A9B95")
    then:
    RuntimeException exception = thrown()
    exception.getMessage() == "The microservice pattern is not supported: A9B95"
  }

  def "createMicroservice"() {
    given:
    when:
    def result = fileReader.createMicroservice("AB95")
    then:
    result.getName() == "A"
    result.getConnections().get(0).getEndpoint() == "B"
    result.getConnections().get(0).getLatency() == 95
  }
}
