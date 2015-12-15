package org.metadatacenter.ingestors.geo.metadata;

import org.metadatacenter.ingestors.geo.GEOSubmissionMetadata2InvestigationConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Metadata for a single GEO submission, which will contain a single series and its associated samples,
 * protocol, and platform.
 * </p>
 * A {@link org.metadatacenter.ingestors.geo.metadb.GEOmetadbIngestor} or a
 * {@link org.metadatacenter.ingestors.geo.soft.GEOSoftIngestor} can
 * be used to read this metadata from GEOmetadb and GEOSoft formats, respectively
 * </p>
 * A {@link GEOSubmissionMetadata2InvestigationConverter} converts this metadata
 * to CEDAR Investigation Model instances.
 *
 * @see Series
 * @see Sample
 * @see Platform
 * @see Protocol
 * @see org.metadatacenter.ingestors.geo.soft.GEOSoftIngestor
 * @see org.metadatacenter.ingestors.geo.metadb.GEOmetadbIngestor
 * @see GEOSubmissionMetadata2InvestigationConverter
 */
public class GEOSubmissionMetadata
{
  private final Series series;
  private final Map<String, Sample> samples; // sampleID -> Sample
  private final Optional<Protocol> protocol;
  private final List<Platform> platforms;

  public GEOSubmissionMetadata(Series series, Map<String, Sample> samples, Optional<Protocol> protocol,
    List<Platform> platforms)
  {
    this.series = series;
    this.samples = Collections.unmodifiableMap(samples);
    this.protocol = protocol;
    this.platforms = Collections.unmodifiableList(platforms);
  }

  public String getGSE() { return series.getGSE(); }

  public Series getSeries()
  {
    return series;
  }

  public Map<String, Sample> getSamples()
  {
    return Collections.unmodifiableMap(samples);
  }

  public Optional<Protocol> getProtocol()
  {
    return this.protocol;
  }

  public List<Platform> getPlatforms() { return this.platforms; }

  @Override public String toString()
  {
    return "GEOMetadata{" +
      "Series=" + series + "\n\n" +
      "Samples=" + prettyPrintSamples() + "\n\n" +
      "Protocol=" + protocol + "\n\n" +
      "Platforms=" + platforms +
      '}';
  }

  private String prettyPrintSamples()
  {
    StringBuilder sb = new StringBuilder();

    for (String sampleName : this.samples.keySet()) {
      Sample sample = this.samples.get(sampleName);
      sb.append("\nsampleName=" + sampleName + ", " + sample);
    }
    return sb.toString();
  }
}
