package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class ValueElement extends MetadataTemplateElement
{
  private final String _value;

  public ValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String _value)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this._value = _value;
  }

  public String getValue()
  {
    return _value;
  }
}
