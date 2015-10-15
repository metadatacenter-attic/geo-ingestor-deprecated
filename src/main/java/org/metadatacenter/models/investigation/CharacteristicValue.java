package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CharacteristicValue extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.URI_BASE + "CharacteristicValue");

  private final StringValueElement value;
  private final Optional<StringValueElement> type;
  private final Optional<StringValueElement> unit;

  public CharacteristicValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement value,
    Optional<StringValueElement> type, Optional<StringValueElement> unit)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.unit = unit;
    this.value = value;
  }

  public CharacteristicValue(StringValueElement value, Optional<StringValueElement> type,
    Optional<StringValueElement> unit)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
  }

  public CharacteristicValue(StringValueElement value)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.value = value;
    this.type = Optional.empty();
    this.unit = Optional.empty();
  }
}
