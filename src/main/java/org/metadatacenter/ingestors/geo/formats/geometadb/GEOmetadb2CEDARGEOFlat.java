package org.metadatacenter.ingestors.geo.formats.geometadb;

import org.metadatacenter.converters.geo.GEOSubmissionMetadata2GEOFlatConverter;
import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.models.geoflat.GEOFlatTemplateInstance;
import org.metadatacenter.repository.model.MetadataTemplateInstanceJSONSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Convert GEO metadata contained in a GEOmetadb database to instances of a CEDAR GEOFlat template.
 */
public class GEOmetadb2CEDARGEOFlat
{
  public static void main(String[] args)
  {
    if (args.length != 4)
      Usage();

    String geometadbFilename = args[0];
    String cedarJSONDirectoryName = args[1];
    int startSeriesIndex = Integer.parseInt(args[2]);
    int numberOfSeries = Integer.parseInt(args[3]);

    try {
      GEOSubmissionMetadata2GEOFlatConverter converter = new GEOSubmissionMetadata2GEOFlatConverter();
      GEOmetadbIngestor geometadbIngestor = new GEOmetadbIngestor(geometadbFilename);
      MetadataTemplateInstanceJSONSerializer<GEOFlatTemplateInstance> geoFlatJSONSerializer = new MetadataTemplateInstanceJSONSerializer<>();
      List<GEOSubmissionMetadata> geoSubmissionsMetadata = geometadbIngestor
        .extractGEOSubmissionsMetadata(startSeriesIndex, numberOfSeries);

      for (GEOSubmissionMetadata geoSubmissionMetadata : geoSubmissionsMetadata) {
        List<GEOFlatTemplateInstance> geoFlatTemplateInstanceList = converter
          .convertGEOSubmissionMetadata2GEOFlatTemplateInstances(geoSubmissionMetadata);
        int index = 1;
        for (GEOFlatTemplateInstance geoFlatTemplateInstance : geoFlatTemplateInstanceList) {
          geoFlatJSONSerializer.serialize(geoFlatTemplateInstance,
            cedarJSONDirectoryName + File.separator + "GEOFlat_" + geoSubmissionMetadata.getGSE() + "_" + index
              + ".json");
          index++;
        }
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
      + " <GEOmetadb Filename> <JSON Directory Name> <Start Series Index> <Number of Series>");
    System.exit(-1);
  }
}

