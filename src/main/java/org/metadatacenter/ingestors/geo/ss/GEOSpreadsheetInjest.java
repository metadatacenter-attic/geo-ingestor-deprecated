package org.metadatacenter.ingestors.geo.ss;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.GEOMetadata2InvestigationConverter;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.repository.model.MetadataTemplateJSONSerializer;

import java.io.IOException;

public class GEOSpreadsheetInjest
{
  public static void main(String[] args)
  {
    if (args.length != 2)
      Usage();

    String geoExcelFilename = args[0];
    String cedarJSONFilename = args[1];

    try {
      GEOMetadata2InvestigationConverter converter = new GEOMetadata2InvestigationConverter();
      GEOSpreadsheetIngestor geoSpreadsheetIngestor = new GEOSpreadsheetIngestor(geoExcelFilename);
      MetadataTemplateJSONSerializer<Investigation> investigationJSONSerializer = new MetadataTemplateJSONSerializer<>(
        cedarJSONFilename);
      GEOMetadata geoMetadata = geoSpreadsheetIngestor.extractGEOMetadata();
      Investigation investigation = converter.convertGeoMetadata2Investigation(geoMetadata);

      investigationJSONSerializer.serialize(investigation);
    } catch (GEOIngestorException e) {
      System.err.println(GEOSpreadsheetInjest.class.getName() + ": Error ingesting: " + e.getMessage());
    } catch (IOException e) {
      System.err.println(GEOSpreadsheetInjest.class.getName() + ": IO error: " + e.getMessage());
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEOSpreadsheetInjest.class.getName() + " <GEOFileName> <CEDARFileName>");
    System.exit(1);
  }
}

