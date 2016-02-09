package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProtocolParameter extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "ProtocolParameter");

  private final StringTemplateFieldInstance name;
  private final Optional<StringTemplateFieldInstance> description;
  private final Optional<ParameterValue> parameterValue;

  public ProtocolParameter(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    Optional<StringTemplateFieldInstance> description, Optional<ParameterValue> parameterValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.parameterValue = parameterValue;
  }

  public ProtocolParameter(StringTemplateFieldInstance name, Optional<StringTemplateFieldInstance> description,
    Optional<ParameterValue> parameterValue)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = description;
    this.parameterValue = parameterValue;
  }

  public ProtocolParameter(StringTemplateFieldInstance name)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = Optional.empty();
    this.parameterValue = Optional.empty();
  }
}
