package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ParameterValue extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "ParameterValue");

  private final StringValueElement value;
  private final Optional<StringValueElement> type;
  private final Optional<StringValueElement> unit;
  private final Optional<ProtocolParameter> protocolParameter;

  public ParameterValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement value,
    Optional<StringValueElement> type, Optional<StringValueElement> unit, Optional<ProtocolParameter> protocolParameter)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.protocolParameter = protocolParameter;
  }

  public ParameterValue(StringValueElement value, Optional<StringValueElement> type, Optional<StringValueElement> unit,
    Optional<ProtocolParameter> protocolParameter)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.protocolParameter = protocolParameter;
  }

  public ParameterValue(StringValueElement value, Optional<StringValueElement> type, Optional<StringValueElement> unit)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.protocolParameter = Optional.empty();
  }

  public ParameterValue(StringValueElement value)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = Optional.empty();
    this.unit = Optional.empty();
    this.protocolParameter = Optional.empty();
  }
}
