package de.oschlies.config;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import de.oschlies.service.cmd.CommandLineReader;
import de.oschlies.service.cmd.CommandLineReaderImpl;
import de.oschlies.service.file.FileReader;
import de.oschlies.service.file.FileReaderImpl;
import de.oschlies.service.tracing.TraceBuilder;
import de.oschlies.service.tracing.TraceBuilderImpl;
import de.oschlies.service.tracing.hops.TraceHops;
import de.oschlies.service.tracing.hops.TraceHopsExact;
import de.oschlies.service.tracing.hops.TraceHopsExactFactory;
import de.oschlies.service.tracing.hops.TraceHopsMax;
import de.oschlies.service.tracing.hops.TraceHopsMaxFactory;
import de.oschlies.service.tracing.latency.TraceMaxLatency;
import de.oschlies.service.tracing.latency.TraceMaxLatencyFactory;
import de.oschlies.service.tracing.latency.TraceMaxLatencyImpl;
import de.oschlies.service.tracing.path.ShortestPath;
import de.oschlies.service.tracing.path.ShortestPathFactory;
import de.oschlies.service.tracing.path.ShortestPathImpl;
import de.oschlies.service.tracing.route.RouteLatencyCalculator;
import de.oschlies.service.tracing.route.RouteLatencyCalculatorImpl;
import de.oschlies.service.tracing.route.RouteLatencyFactory;

public class InstanaModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CommandLineReader.class).to(CommandLineReaderImpl.class);
    bind(TraceBuilder.class).to(TraceBuilderImpl.class);
    bind(FileReader.class).to(FileReaderImpl.class);
    binder().install(new FactoryModuleBuilder()
        .implement(RouteLatencyCalculator.class, RouteLatencyCalculatorImpl.class)
        .build(RouteLatencyFactory.class));
    binder().install(new FactoryModuleBuilder()
        .implement(ShortestPath.class, ShortestPathImpl.class)
        .build(ShortestPathFactory.class));
    binder().install(new FactoryModuleBuilder()
        .implement(TraceHops.class, TraceHopsExact.class)
        .build(TraceHopsExactFactory.class));
    binder().install(new FactoryModuleBuilder()
        .implement(TraceHops.class, TraceHopsMax.class)
        .build(TraceHopsMaxFactory.class));

    binder().install(new FactoryModuleBuilder()
        .implement(TraceMaxLatency.class, TraceMaxLatencyImpl.class)
        .build(TraceMaxLatencyFactory.class));
  }
}
