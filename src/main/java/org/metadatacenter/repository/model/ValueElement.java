package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class ValueElement extends MetadataTemplateElement
{
  private final String _value;

  public ValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String _value)
  {
    super(createJSONLDContext(), jsonLDTypes, jsonLDIdentifier);
    this._value = _value;
  }

  public String getValue()
  {
    return _value;
  }

  private static Optional<JSONLDContext> createJSONLDContext()
  {
    JSONLDContextEntry jsonLDContextEntry = new JSONLDContextEntry("_value", "https://schema.org/value");

    return Optional.of(new JSONLDContext(jsonLDContextEntry));
  }
}
