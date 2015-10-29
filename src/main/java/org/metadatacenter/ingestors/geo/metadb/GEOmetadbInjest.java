package org.metadatacenter.ingestors.geo.metadb;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.GEOMetadata2InvestigationConverter;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.repository.model.MetadataTemplateJSONSerializer;

import java.io.IOException;

public class GEOmetadbInjest
{
  public static void main(String[] args)
  {
    if (args.length != 2)
      Usage();

    String geometadbFilename = args[0];
    String cedarJSONFilename = args[1];

    try {
      GEOMetadata2InvestigationConverter converter = new GEOMetadata2InvestigationConverter();
      GEOmetadbIngestor geometadbIngestor = new GEOmetadbIngestor(geometadbFilename);
      MetadataTemplateJSONSerializer<Investigation> investigationJSONSerializer = new MetadataTemplateJSONSerializer<>(
        cedarJSONFilename);
      GEOMetadata geoMetadata = geometadbIngestor.extractGEOMetadata(1);
      Investigation investigation = converter.convertGeoMetadata2Investigation(geoMetadata);

      investigationJSONSerializer.serialize(investigation);

    } catch (GEOIngestorException e) {
      System.err.println(GEOmetadbInjest.class.getName() + ": Error ingesting: " + e.getMessage());
      System.exit(-1);
    } catch (IOException e) {
      System.err.println(GEOmetadbInjest.class.getName() + ": IO error ingesting: " + e.getMessage());
      System.exit(-1);
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEOmetadbInjest.class.getName() + " <GEOmetadb Filename> <CEDAR JSON Filename>");
    System.exit(-1);
  }
}

