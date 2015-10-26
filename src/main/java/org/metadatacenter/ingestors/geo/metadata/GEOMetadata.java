package org.metadatacenter.ingestors.geo.metadata;

import org.metadatacenter.ingestors.geo.soft.GEOSoftIngestor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * GEO metadata. This metadata is typically extracted from a metadata sheet in a GEO submission spreadsheet.
 * See http://www.ncbi.nlm.nih.gov/geo/info/spreadsheet.html#GAmeta for a description of this metadata.
 *
 *  TODO: variables and repeat fields in Series not currently read
 *  TODO: multi-channel metadata samples not currently handled. See ./examples/GA_Agilent_two_color_matrix.xls
 *
 *
 * @see GEOSoftIngestor
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
      "Series=" + series + "\n\n" +
      "Samples=" + ppSamples() +  "\n\n" +
      "Protocol=" + protocol + "\n\n" +
      "Platform=" + platform +
      '}';
  }

  private String ppSamples()
  {
    StringBuilder sb = new StringBuilder();

    for (String sampleName : this.samples.keySet()) {
      Sample sample = this.samples.get(sampleName);
      sb.append("\nsampleName=" + sampleName + ", " + sample);
    }
    return sb.toString();
  }
}
