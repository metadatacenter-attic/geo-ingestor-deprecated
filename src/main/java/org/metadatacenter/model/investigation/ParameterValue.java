package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class ParameterValue extends MetadataTemplateElement
{
  private final StringValueElement type;
  private final StringValueElement unit;
  private final StringValueElement value;
  private final Optional<ProtocolParameter> hasProtocolParameter;

  public ParameterValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement type,
    StringValueElement unit, StringValueElement value, Optional<ProtocolParameter> protocolParameter)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.unit = unit;
    this.value = value;
    this.hasProtocolParameter = protocolParameter;
  }
}
