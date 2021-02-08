package de.oschlies.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Microservice {

  private String name;
  private List<Connection> connections = new LinkedList<>();
}
