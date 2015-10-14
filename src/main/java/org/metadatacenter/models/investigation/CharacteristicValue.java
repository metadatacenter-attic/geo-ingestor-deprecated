package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.List;
import java.util.Optional;

public class CharacteristicValue  extends MetadataTemplateElement
{
  private final StringValueElement type;
  private final StringValueElement unit;
  private final StringValueElement value;

  public CharacteristicValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement type,
      StringValueElement unit, StringValueElement value)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.unit = unit;
    this.value = value;
  }
}
