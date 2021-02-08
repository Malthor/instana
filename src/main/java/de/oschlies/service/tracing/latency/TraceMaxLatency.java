package de.oschlies.service.tracing.latency;

public interface TraceMaxLatency {

  public int calculateMaxLatency(String start,String endpoint, int latency);
}
