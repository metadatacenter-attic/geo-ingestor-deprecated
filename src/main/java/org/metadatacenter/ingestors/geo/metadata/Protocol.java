package org.metadatacenter.ingestors.geo.metadata;

import java.util.Optional;

public class Protocol
{
  private final Optional<String> growth;
  private final Optional<String> treatment;
  private final Optional<String> extract;
  private final Optional<String> label;
  private final Optional<String> hyb;
  private final Optional<String> scan;
  private final Optional<String> dataProcessing;
  private final Optional<String> valueDefinition;

  public Protocol(Optional<String> growth, Optional<String> treatment, Optional<String> extract, Optional<String> label,
    Optional<String> hyb, Optional<String> scan, Optional<String> dataProcessing, Optional<String> valueDefinition)
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

  public Optional<String> getExtract()
  {
    return extract;
  }

  public Optional<String> getLabel()
  {
    return label;
  }

  public Optional<String> getHyb()
  {
    return hyb;
  }

  public Optional<String> getScan()
  {
    return scan;
  }

  public Optional<String> getDataProcessing()
  {
    return dataProcessing;
  }

  public Optional<String> getValueDefinition()
  {
    return valueDefinition;
  }

  @Override public String toString()
  {
    return "Protocol{" +
      "\n growth=" + growth +
      "\n treatment=" + treatment +
      "\n extract='" + extract + '\'' +
      "\n label='" + label + '\'' +
      "\n hyb='" + hyb + '\'' +
      "\n scan='" + scan + '\'' +
      "\n dataProcessing='" + dataProcessing + '\'' +
      "\n valueDefinition='" + valueDefinition + '\'' +
      '}';
  }
}
