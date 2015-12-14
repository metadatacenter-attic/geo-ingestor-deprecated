package org.metadatacenter.repository.model;

public class JSONLDContextEntry
{
  private final String propertyName;
  private final String propertyURI;

  public JSONLDContextEntry(String propertyName, String propertyURI)
  {
    this.propertyName = propertyName;
    this.propertyURI = propertyURI;
  }

  public String getPropertyName()
  {
    return propertyName;
  }

  public String getPropertyURI()
  {
    return propertyURI;
  }
}
