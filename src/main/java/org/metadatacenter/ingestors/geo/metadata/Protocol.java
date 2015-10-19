package org.metadatacenter.ingestors.geo.metadata;

import java.util.List;

public class Protocol
{
  private final List<String> growth;
  private final List<String> treatment;
  private final List<String> extract;
  private final List<String> label;
  private final List<String> hyb;
  private final List<String> scan;
  private final List<String> dataProcessing;
  private final List<String> valueDefinition;

  public Protocol(List<String> growth, List<String> treatment, List<String> extract, List<String> label,
    List<String> hyb, List<String> scan, List<String> dataProcessing, List<String> valueDefinition)
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

  public List<String> getGrowth()
  {
    return growth;
  }

  public List<String> getTreatment()
  {
    return treatment;
  }

  public List<String> getExtract()
  {
    return extract;
  }

  public List<String> getLabel()
  {
    return label;
  }

  public List<String> getHyb()
  {
    return hyb;
  }

  public List<String> getScan()
  {
    return scan;
  }

  public List<String> getDataProcessing()
  {
    return dataProcessing;
  }

  public List<String> getValueDefinition()
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
