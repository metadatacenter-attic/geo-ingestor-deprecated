package org.metadatacenter.ingestors.geo.metadata;

import java.util.List;
import java.util.Map;

public class PerChannelSampleInfo
{
  private final Integer channelNumber;
  private final String sourceName;
  private final List<String> organisms;
  private final Map<String, String> characteristics; // characteristic -> value
  private final String molecule;
  private final String label;

  public PerChannelSampleInfo(Integer channelNumber, String sourceName, List<String> organisms,
      Map<String, String> characteristics, String molecule, String label)
  {
    this.channelNumber = channelNumber;
    this.sourceName = sourceName;
    this.organisms = organisms;
    this.characteristics = characteristics;
    this.molecule = molecule;
    this.label = label;
  }

  public Integer getChannelNumber()
  {
    return channelNumber;
  }

  public String getSourceName()
  {
    return sourceName;
  }

  public List<String> getOrganisms()
  {
    return organisms;
  }

  public Map<String, String> getCharacteristics()
  {
    return characteristics;
  }

  public String getMolecule()
  {
    return molecule;
  }

  public String getLabel()
  {
    return label;
  }
}
