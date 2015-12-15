package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Characteristic extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "Characteristic");

  private final StringValueElement name;
  private final Optional<StringValueElement> description;
  private final Optional<CharacteristicValue> characteristicValue;

  public Characteristic(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    Optional<StringValueElement> description, Optional<CharacteristicValue> characteristicValue)

  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.characteristicValue = characteristicValue;
  }

  public Characteristic(StringValueElement name, Optional<StringValueElement> description,
    Optional<CharacteristicValue> characteristicValue)

  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = description;
    this.characteristicValue = characteristicValue;
  }

  public Characteristic(StringValueElement name, Optional<CharacteristicValue> characteristicValue)

  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = Optional.empty();
    this.characteristicValue = characteristicValue;
  }
}
