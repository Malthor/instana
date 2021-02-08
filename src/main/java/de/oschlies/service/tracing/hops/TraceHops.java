package de.oschlies.service.tracing.hops;

public interface TraceHops {

  public int calculateHops(String start, String endpoint, int hops);
}
