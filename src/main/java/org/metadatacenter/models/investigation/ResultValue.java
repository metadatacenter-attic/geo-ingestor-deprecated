package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResultValue extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE
    + "ResultValue");

  private final StringValueElement value;
  private final Optional<StringValueElement> type;
  private final Optional<StringValueElement> unit;

  public ResultValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement value,
    Optional<StringValueElement> type, Optional<StringValueElement> unit)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
    this.type = type;
    this.unit = unit;
  }

  public ResultValue(StringValueElement value, Optional<StringValueElement> type, Optional<StringValueElement> unit)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
  }

  public ResultValue(StringValueElement value)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = Optional.empty();
    this.unit = Optional.empty();
  }
}

