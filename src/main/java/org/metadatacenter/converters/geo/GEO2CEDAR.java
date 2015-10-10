package org.metadatacenter.converters.geo;

public class GEO2CEDAR
{
  public static void main(String[] args)
  {
    if (args.length != 2)
      Usage();

    String geoFile = args[0];
    String cedarFile = args[1];
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEO2CEDAR.class.getName() + " <GEOFileName> <CEDARFileName> ]");
    System.exit(1);
  }
}

