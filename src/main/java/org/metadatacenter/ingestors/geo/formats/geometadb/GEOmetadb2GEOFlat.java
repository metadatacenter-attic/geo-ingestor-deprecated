package org.metadatacenter.ingestors.geo.formats.geometadb;

import org.metadatacenter.converters.geo.GEOSubmissionMetadata2GEOFlatConverter;
import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.models.geoflat.GEOFlat;
import org.metadatacenter.repository.model.MetadataTemplateInstanceJSONSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Convert GEO metadata contained in a GEOmetadb database to instances of a CEDAR GEOFlat template.
 */
public class GEOmetadb2GEOFlat
{
  public static void main(String[] args)
  {
    if (args.length != 3)
      Usage();

    String geometadbFilename = args[0];
    String cedarJSONDirectoryName = args[1];
    int numberOfSeries = Integer.parseInt(args[2]);

    try {
      GEOSubmissionMetadata2GEOFlatConverter converter = new GEOSubmissionMetadata2GEOFlatConverter();
      GEOmetadbIngestor geometadbIngestor = new GEOmetadbIngestor(geometadbFilename);
      MetadataTemplateInstanceJSONSerializer<GEOFlat> geoFlatJSONSerializer = new MetadataTemplateInstanceJSONSerializer<>();
      List<GEOSubmissionMetadata> geoSubmissionsMetadata = geometadbIngestor
        .extractGEOSubmissionsMetadata(numberOfSeries);

      for (GEOSubmissionMetadata geoSubmissionMetadata : geoSubmissionsMetadata) {
        GEOFlat geoFlat = converter.convertGEOSubmissionMetadata2GEOFlat(geoSubmissionMetadata);
        geoFlatJSONSerializer
          .serialize(geoFlat, cedarJSONDirectoryName + File.separator + "GEOFlat_" + geoSubmissionMetadata.getGSE());
      }
    } catch (GEOIngestorException e) {
      System.err.println(GEOmetadb2Investigations.class.getName() + ": Error ingesting: " + e.getMessage());
      System.exit(-1);
    } catch (IOException e) {
      System.err.println(GEOmetadb2Investigations.class.getName() + ": IO error ingesting: " + e.getMessage());
      System.exit(-1);
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + GEOmetadb2Investigations.class.getName()
      + " <GEOmetadb Filename> <JSON Directory Name> <Number of Series>");
    System.exit(-1);
  }
}

