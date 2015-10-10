package org.metadatacenter.converters.geo.metadata;

import java.util.Optional;

public class Protocol
{
  private final Optional<String> growth;
  private final Optional<String> treatment;
  private final String extract;
  private final String label;
  private final String hyb;
  private final String scan;
  private final String dataProcessing;
  private final String valueDefinition;

  public Protocol(Optional<String> growth, Optional<String> treatment, String extract, String label, String hyb,
    String scan, String dataProcessing, String valueDefinition)
  {
    this.growth = growth;
    this.treatment = treatment;
    this.extract = extract;
    this.label = label;
    this.hyb = hyb;
    this.scan = scan;
    this.dataProcessing = dataProcessing;
    this.valueDefinition = valueDefinition;
  }

  public Optional<String> getGrowth()
  {
    return growth;
  }

  public Optional<String> getTreatment()
  {
    return treatment;
  }

  public String getExtract()
  {
    return extract;
  }

  public String getLabel()
  {
    return label;
  }

  public String getHyb()
  {
    return hyb;
  }

  public String getScan()
  {
    return scan;
  }

  public String getDataProcessing()
  {
    return dataProcessing;
  }

  public String getValueDefinition()
  {
    return valueDefinition;
  }
}
