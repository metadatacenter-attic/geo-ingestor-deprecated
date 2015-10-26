package org.metadatacenter.ingestors.geo.soft;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.GEOMetadata2InvestigationConverter;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.repository.model.MetadataTemplateJSONSerializer;

import java.io.IOException;

public class GEOSoftInjest
{
  public static void main(String[] args)
  {
    if (args.length != 2)
      Usage();

    String geoExcelFilename = args[0];
    String cedarJSONFilename = args[1];

    try {
      GEOMetadata2InvestigationConverter converter = new GEOMetadata2InvestigationConverter();
      GEOSoftIngestor geoSoftIngestor = new GEOSoftIngestor(geoExcelFilename);
      MetadataTemplateJSONSerializer<Investigation> investigationJSONSerializer = new MetadataTemplateJSONSerializer<>(
        cedarJSONFilename);
      GEOMetadata geoMetadata = geoSoftIngestor.extractGEOMetadata();
      Investigation investigation = converter.convertGeoMetadata2Investigation(geoMetadata);

      investigationJSONSerializer.serialize(investigation);
    } catch (GEOIngestorException e) {
      System.err.println(GEOSoftInjest.class.getName() + ": Error ingesting: " + e.getMessage());
      System.exit(-1);
    } catch (IOException e) {
      System.err.println(GEOSoftInjest.class.getName() + ": IO error: " + e.getMessage());
      System.exit(-1);
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEOSoftInjest.class.getName() + " <GEO Excel Filename> <CEDAR JSON File Name>");
    System.exit(-1);
  }
}

