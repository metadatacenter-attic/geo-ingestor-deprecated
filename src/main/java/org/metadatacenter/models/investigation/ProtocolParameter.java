package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProtocolParameter extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.URI_BASE + "ProtocolParameter");

  private final StringValueElement name;
  private final Optional<StringValueElement> description;
  private final Optional<ParameterValue> parameterValue;

  public ProtocolParameter(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    Optional<StringValueElement> description, Optional<ParameterValue> parameterValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.parameterValue = parameterValue;
  }

  public ProtocolParameter(StringValueElement name, Optional<StringValueElement> description,
    Optional<ParameterValue> parameterValue)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.name = name;
    this.description = description;
    this.parameterValue = parameterValue;
  }

  public ProtocolParameter(StringValueElement name)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.name = name;
    this.description = Optional.empty();
    this.parameterValue = Optional.empty();
  }
}
