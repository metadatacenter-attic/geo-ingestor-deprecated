package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CharacteristicValue extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "CharacteristicValue");

  private final StringTemplateFieldInstance value;
  private final Optional<StringTemplateFieldInstance> type;
  private final Optional<StringTemplateFieldInstance> unit;

  public CharacteristicValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    StringTemplateFieldInstance value, Optional<StringTemplateFieldInstance> type,
    Optional<StringTemplateFieldInstance> unit)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.unit = unit;
    this.value = value;
  }

  public CharacteristicValue(StringTemplateFieldInstance value, Optional<StringTemplateFieldInstance> type,
    Optional<StringTemplateFieldInstance> unit)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = type;
    this.unit = unit;
  }

  public CharacteristicValue(StringTemplateFieldInstance value)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.value = value;
    this.type = Optional.empty();
    this.unit = Optional.empty();
  }
}
