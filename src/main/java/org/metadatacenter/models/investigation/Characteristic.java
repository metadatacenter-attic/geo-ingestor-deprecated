package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Characteristic extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Characteristic");

  private final StringTemplateFieldInstance name;
  private final Optional<StringTemplateFieldInstance> description;
  private final Optional<CharacteristicValue> characteristicValue;

  public Characteristic(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    Optional<StringTemplateFieldInstance> description, Optional<CharacteristicValue> characteristicValue)

  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.characteristicValue = characteristicValue;
  }

  public Characteristic(StringTemplateFieldInstance name, Optional<StringTemplateFieldInstance> description,
    Optional<CharacteristicValue> characteristicValue)

  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = description;
    this.characteristicValue = characteristicValue;
  }

  public Characteristic(StringTemplateFieldInstance name, Optional<CharacteristicValue> characteristicValue)

  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = Optional.empty();
    this.characteristicValue = characteristicValue;
  }
}
