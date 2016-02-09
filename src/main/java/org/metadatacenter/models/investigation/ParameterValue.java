package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ParameterValue extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "ParameterValue");

  private final StringTemplateFieldInstance value;
  private final Optional<StringTemplateFieldInstance> type;
  private final Optional<StringTemplateFieldInstance> unit;
  private final Optional<ProtocolParameter> protocolParameter;

  public ParameterValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance value,
    Optional<StringTemplateFieldInstance> type, Optional<StringTemplateFieldInstance> unit,
    Optional<ProtocolParameter> protocolParameter)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.protocolParameter = protocolParameter;
  }

  public ParameterValue(StringTemplateFieldInstance value, Optional<StringTemplateFieldInstance> type,
    Optional<StringTemplateFieldInstance> unit, Optional<ProtocolParameter> protocolParameter)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.protocolParameter = protocolParameter;
  }

  public ParameterValue(StringTemplateFieldInstance value, Optional<StringTemplateFieldInstance> type,
    Optional<StringTemplateFieldInstance> unit)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.protocolParameter = Optional.empty();
  }

  public ParameterValue(StringTemplateFieldInstance value)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = Optional.empty();
    this.unit = Optional.empty();
    this.protocolParameter = Optional.empty();
  }
}
