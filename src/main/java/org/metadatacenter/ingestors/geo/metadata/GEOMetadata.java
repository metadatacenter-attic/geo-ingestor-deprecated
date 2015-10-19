package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * GEO metadata. This metadata is typically extracted from a metadata sheet in a GEO submission spreadsheet.
 * See http://www.ncbi.nlm.nih.gov/geo/info/spreadsheet.html#GAmeta for a description of this metadata.
 *
 * @see org.metadatacenter.ingestors.geo.GEOSpreadsheetHandler
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

  @Override public String toString()
  {
    return "GEOMetadata{" +
      "series=" + series +
      ", samples=" + samples +
      ", protocol=" + protocol +
      ", platform=" + platform +
      '}';
  }
}
