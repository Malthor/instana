package de.oschlies.service.tracing;

public interface TraceMaxLatency {

  public int calculateMaxLatency(String start,String endpoint, int latency);
}
