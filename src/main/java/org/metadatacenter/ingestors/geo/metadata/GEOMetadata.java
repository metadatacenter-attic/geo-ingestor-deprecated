package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Metadata for a GEO series and its associated samples, protocol, and platform.
 * </p>
 * A {@link org.metadatacenter.ingestors.geo.metadb.GEOmetadbIngestor} or a
 * {@link org.metadatacenter.ingestors.geo.soft.GEOSoftIngestor} can
 * be used to read this metadata from GEOmetadb and GEOSoft formats, respectively
 * </p>
 * A {@link org.metadatacenter.ingestors.geo.GEOMetadata2InvestigationConverter} converts this metadata
 * to CEDAR Investigation Model instances.
 *
 * @see Series
 * @see Sample
 * @see Platform
 * @see Protocol
 * @see org.metadatacenter.ingestors.geo.soft.GEOSoftIngestor
 * @see org.metadatacenter.ingestors.geo.metadb.GEOmetadbIngestor
 * @see org.metadatacenter.ingestors.geo.GEOMetadata2InvestigationConverter
 */
public class GEOMetadata
{
  private final Series series;
  private final Map<String, Sample> samples; // sampleID -> Sample
  private final Protocol protocol;
  private final Optional<Platform> platform;

  public GEOMetadata(Series series, Map<String, Sample> samples, Protocol protocol, Optional<Platform> platform)
  {
    this.series = series;
    this.samples = samples;
    this.protocol = protocol;
    this.platform = platform;
  }

  public Series getSeries()
  {
    return series;
  }

  public Map<String, Sample> getSamples()
  {
    return Collections.unmodifiableMap(samples);
  }

  public Protocol getProtocol()
  {
    return this.protocol;
  }

  public Optional<Platform> getPlatform() { return this.platform; }

  @Override public String toString()
  {
    return "GEOMetadata{" +
      "Series=" + series + "\n\n" +
      "Samples=" + prettyPrintSamples() + "\n\n" +
      "Protocol=" + protocol + "\n\n" +
      "Platform=" + platform +
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
