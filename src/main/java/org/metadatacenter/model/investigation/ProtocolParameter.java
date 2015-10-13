package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class ProtocolParameter extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement description;
  private final Optional<ParameterValue> hasParameterValue;

  public ProtocolParameter(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement description, Optional<ParameterValue> parameterValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.hasParameterValue = parameterValue;
  }
}
