package de.oschlies.main;


import com.google.inject.Guice;
import com.google.inject.Injector;
import de.oschlies.config.InstanaModule;
import de.oschlies.service.cmd.CommandLineReader;
import de.oschlies.service.tracing.TraceBuilder;

public class Application {

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new InstanaModule());
    CommandLineReader commandLineReader = injector.getInstance(CommandLineReader.class);
    commandLineReader.readFileLocation(args);
    TraceBuilder traceBuilder = injector.getInstance(TraceBuilder.class);
      traceBuilder.createTrace(args);

  }

}
