package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.List;
import java.util.Optional;

public class ParameterValue extends MetadataTemplateElement
{
  private final StringValueElement value;
  private final Optional<StringValueElement> type;
  private final Optional<StringValueElement> unit;
  private final Optional<ProtocolParameter> hasProtocolParameter;

  public ParameterValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement value,
    Optional<StringValueElement> type, Optional<StringValueElement> unit, Optional<ProtocolParameter> protocolParameter)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.hasProtocolParameter = protocolParameter;
  }

  public ParameterValue(StringValueElement value, Optional<StringValueElement> type, Optional<StringValueElement> unit,
    Optional<ProtocolParameter> protocolParameter)
  {
    super();
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.hasProtocolParameter = protocolParameter;
  }

  public ParameterValue(StringValueElement value, Optional<StringValueElement> type, Optional<StringValueElement> unit)
  {
    super();
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.hasProtocolParameter = Optional.empty();
  }
}
