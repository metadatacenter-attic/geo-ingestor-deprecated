package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class Characteristic  extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement description;
  private final Optional<CharacteristicValue> hasCharacteristicValue;

  public Characteristic(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement description, Optional<CharacteristicValue> characteristicValue)

  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.hasCharacteristicValue = characteristicValue;
  }
}
