package org.metadatacenter.ingestors.geo.metadata;

import java.util.List;
import java.util.Map;

public class PerChannelSampleInfo
{
  private final String channelName;
  private final String sourceName;
  private final List<String> organisms;
  private final Map<String, String> characteristics; // characteristic -> value
  private final String molecule;
  private final String label;

  public PerChannelSampleInfo(String channelName, String sourceName, List<String> organisms,
      Map<String, String> characteristics, String molecule, String label)
  {
    this.channelName = channelName;
    this.sourceName = sourceName;
    this.organisms = organisms;
    this.characteristics = characteristics;
    this.molecule = molecule;
    this.label = label;
  }
}
