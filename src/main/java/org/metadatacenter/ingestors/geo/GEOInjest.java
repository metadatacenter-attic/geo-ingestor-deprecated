package org.metadatacenter.ingestors.geo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.models.investigation.Investigation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class GEOInjest
{
  public static void main(String[] args)
  {
    if (args.length != 2)
      Usage();

    String geoExcelFile = args[0];
    String cedarJSONFile = args[1];

    try {
      GEOMetadata2InvestigationConverter converter = new GEOMetadata2InvestigationConverter();
      GEOSpreadsheetHandler geoSpreadsheetHandler = new GEOSpreadsheetHandler(geoExcelFile);
      GEOMetadata geoMetadata = geoSpreadsheetHandler.extractGEOMetadata();
      Investigation investigation = converter.convertGeoMetadata2Investigation(geoMetadata);

      writeInvestigation2CEDARJSONFile(investigation, cedarJSONFile);
    } catch (GEOIngestorException e) {
      System.err.println("GEO2CEDAR.class.getName(): Error ingesting: " + e.getMessage());
    }
  }

  private static void writeInvestigation2CEDARJSONFile(Investigation investigation, String cedarJSONFile)
  {
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(cedarJSONFile), "UTF-8")) {
      Gson gson = new GsonBuilder().create();
      gson.toJson(investigation, writer);
    } catch (FileNotFoundException e) {
      System.err.println("GEO2CEDAR.class.getName(): Error opening or creating JSON file " + cedarJSONFile);
    } catch (UnsupportedEncodingException e) {
      System.err.println("GEO2CEDAR.class.getName(): Bad JSON file encoding");
    } catch (IOException e) {
      System.err.println("GEO2CEDAR.class.getName(): Error writing JSON file " + cedarJSONFile);
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEOInjest.class.getName() + " <GEOFileName> <CEDARFileName>");
    System.exit(1);
  }
}

