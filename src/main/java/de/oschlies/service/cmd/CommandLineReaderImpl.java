package de.oschlies.service.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineReaderImpl implements CommandLineReader{

  @Override
  public String readFileLocation(String[] args) {
    Options options = new Options();
    Option logfile = Option.builder()
        .longOpt("file")
        .argName("file" )
        .hasArg()
        .desc("Graph input file" )
        .build();

    options.addOption(logfile);
    String fileLocation = "";
    try {
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse( options, args);
      if(cmd.hasOption("file")) {
        fileLocation=  cmd.getOptionValue( "file" );
      }
    } catch (ParseException e) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("Graph Builder", options);
    }
    return fileLocation;
  }
}
