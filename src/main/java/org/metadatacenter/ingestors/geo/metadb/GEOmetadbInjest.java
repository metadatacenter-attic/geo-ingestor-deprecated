package org.metadatacenter.ingestors.geo.metadb;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.GEOSubmissionMetadata2InvestigationConverter;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.repository.model.MetadataTemplateJSONSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GEOmetadbInjest
{
  public static void main(String[] args)
  {
    if (args.length != 3)
      Usage();

    String geometadbFilename = args[0];
    String cedarJSONDirectoryName = args[1];
    int numberOfSeries = Integer.parseInt(args[2]);

    try {
      GEOSubmissionMetadata2InvestigationConverter converter = new GEOSubmissionMetadata2InvestigationConverter();
      GEOmetadbIngestor geometadbIngestor = new GEOmetadbIngestor(geometadbFilename);
      MetadataTemplateJSONSerializer<Investigation> investigationJSONSerializer = new MetadataTemplateJSONSerializer<>();
      List<GEOSubmissionMetadata> geoSubmissionsMetadata = geometadbIngestor
        .extractGEOSubmissionsMetadata(numberOfSeries);

      for (GEOSubmissionMetadata geoSubmissionMetadata : geoSubmissionsMetadata) {
        Investigation investigation = converter.convertGEOSubmissionMetadata2Investigation(geoSubmissionMetadata);
        investigationJSONSerializer.serialize(investigation,
          cedarJSONDirectoryName + File.separator + "Investigation_" + geoSubmissionMetadata.getSeriesID());
      }
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
    System.err.println(
      "Usage: " + GEOmetadbInjest.class.getName() + " <GEOmetadb Filename> <JSON Directory Name> <Number of Series>");
    System.exit(-1);
  }
}

