package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Metadata for GEO. See http://www.ncbi.nlm.nih.gov/geo/info/spreadsheet.html#GAmeta.
 */
public class GEOMetadata
{
  private final Series series;
  private final Map<String, Sample> samples; // sample name -> Sample
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
}
