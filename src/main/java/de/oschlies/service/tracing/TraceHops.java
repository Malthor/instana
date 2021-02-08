package de.oschlies.service.tracing;

public interface TraceHops {

  public int calculateHops(String start,String endpoint, int hops);
}
