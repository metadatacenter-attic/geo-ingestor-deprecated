package org.metadatacenter.ingestors.geo.soft;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.GEOSubmissionMetadata2InvestigationConverter;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
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
      GEOSubmissionMetadata2InvestigationConverter converter = new GEOSubmissionMetadata2InvestigationConverter();
      GEOSoftIngestor geoSoftIngestor = new GEOSoftIngestor(geoExcelFilename);
      MetadataTemplateJSONSerializer<Investigation> investigationJSONSerializer = new MetadataTemplateJSONSerializer<>();
      GEOSubmissionMetadata geoSubmissionMetadata = geoSoftIngestor.extractGEOSubmissionMetadata();
      Investigation investigation = converter.convertGEOSubmissionMetadata2Investigation(geoSubmissionMetadata);

      investigationJSONSerializer.serialize(investigation, cedarJSONFilename);
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

