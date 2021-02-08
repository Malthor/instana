package de.oschlies.config;

import com.google.inject.AbstractModule;
import de.oschlies.service.cmd.CommandLineReader;
import de.oschlies.service.cmd.CommandLineReaderImpl;
import de.oschlies.service.file.FileReader;
import de.oschlies.service.file.FileReaderImpl;
import de.oschlies.service.tracing.TraceBuilder;
import de.oschlies.service.tracing.TraceBuilderImpl;

public class InstanaModule extends AbstractModule {

  @Override
  protected void configure() {
  bind(CommandLineReader.class).to(CommandLineReaderImpl.class);
  bind(TraceBuilder.class).to(TraceBuilderImpl.class);
  bind(FileReader.class).to(FileReaderImpl.class);
  }
}
