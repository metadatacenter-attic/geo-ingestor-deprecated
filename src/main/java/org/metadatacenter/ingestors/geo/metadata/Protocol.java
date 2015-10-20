package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
  private final Map<String, List<String>> userDefinedFields;

  public Protocol(List<String> growth, List<String> treatment, List<String> extract, List<String> label,
    List<String> hyb, List<String> scan, List<String> dataProcessing, List<String> valueDefinition,
    Map<String, List<String>> userDefinedFields)
  {
    this.growth = Collections.unmodifiableList(growth);
    this.treatment = Collections.unmodifiableList(treatment);
    this.extract = Collections.unmodifiableList(extract);
    this.label = Collections.unmodifiableList(label);
    this.hyb = Collections.unmodifiableList(hyb);
    this.scan = Collections.unmodifiableList(scan);
    this.dataProcessing = Collections.unmodifiableList(dataProcessing);
    this.valueDefinition = Collections.unmodifiableList(valueDefinition);
    this.userDefinedFields = Collections.unmodifiableMap(userDefinedFields);
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

  public Map<String, List<String>> getUserDefinedFields()
  {
    return userDefinedFields;
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
