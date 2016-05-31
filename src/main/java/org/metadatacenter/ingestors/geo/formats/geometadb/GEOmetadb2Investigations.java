package org.metadatacenter.ingestors.geo.formats.geometadb;

import org.metadatacenter.converters.geo.GEOSubmissionMetadata2InvestigationConverter;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.readers.geo.GEOReaderException;
import org.metadatacenter.readers.geo.formats.geometadb.GEOmetadbReader;
import org.metadatacenter.readers.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.repository.model.MetadataTemplateInstanceJSONSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Convert GEO metadata contained in a GEOmetadb database to instances of a CEDAR Investigation template.
 */
public class GEOmetadb2Investigations
{
  public static void main(String[] args)
  {
    if (args.length != 4)
      Usage();

    String geometadbFilename = args[0];
    String cedarJSONDirectoryName = args[1];
    int minSeriesIndex = Integer.parseInt(args[2]);
    int numberOfSeries = Integer.parseInt(args[3]);

    try {
      GEOSubmissionMetadata2InvestigationConverter converter = new GEOSubmissionMetadata2InvestigationConverter();
      GEOmetadbReader geometadbReader = new GEOmetadbReader(geometadbFilename);
      MetadataTemplateInstanceJSONSerializer<Investigation> investigationJSONSerializer = new MetadataTemplateInstanceJSONSerializer<>();
      List<GEOSubmissionMetadata> geoSubmissionsMetadata = geometadbReader
        .extractGEOSubmissionsMetadata(minSeriesIndex, numberOfSeries);

      for (GEOSubmissionMetadata geoSubmissionMetadata : geoSubmissionsMetadata) {
        Investigation investigation = converter.convertGEOSubmissionMetadata2Investigation(geoSubmissionMetadata);
        investigationJSONSerializer.serialize(investigation,
          cedarJSONDirectoryName + File.separator + "Investigation_" + geoSubmissionMetadata.getGSE());
      }
    } catch (GEOReaderException e) {
      System.err.println(GEOmetadb2Investigations.class.getName() + ": Error reading: " + e.getMessage());
      System.exit(-1);
    } catch (IOException e) {
      System.err.println(GEOmetadb2Investigations.class.getName() + ": IO error ingesting: " + e.getMessage());
      System.exit(-1);
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEOmetadb2Investigations.class.getName()
      + " <GEOmetadb Filename> <JSON Directory Name> <Min Series Index> <Number of Series>");
    System.exit(-1);
  }
}

