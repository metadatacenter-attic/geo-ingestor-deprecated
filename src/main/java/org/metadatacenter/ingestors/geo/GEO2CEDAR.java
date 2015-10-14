package org.metadatacenter.ingestors.geo;

import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;

public class GEO2CEDAR
{
  public static void main(String[] args)
  {
    if (args.length != 2)
      Usage();

    String geoExcelFile = args[0];
    String cedarJSONFile = args[1];

    try {
      GEOSpreadsheetHandler geoSpreadsheetHandler = new GEOSpreadsheetHandler(geoExcelFile);
      GEOMetadata geoMetadata = geoSpreadsheetHandler.extractGEOMetadata();
    } catch (GEOIngestorException e) {
      e.printStackTrace();
    }
}

  private static void Usage()
  {
    System.err.println("Usage: " + GEO2CEDAR.class.getName() + " <GEOFileName> <CEDARFileName> ]");
    System.exit(1);
  }
}

