package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class TemplateFieldInstance extends MetadataTemplateElementInstance
{
  private final String _value;

  public TemplateFieldInstance(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String _value)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this._value = _value;
  }

  public String getValue()
  {
    return _value;
  }
}
