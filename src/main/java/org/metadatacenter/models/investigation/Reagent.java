package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Reagent extends MetadataTemplateElement implements Input
{
  private final StringValueElement name;
  private final StringValueElement type;
  private final List<Characteristic> hasCharacteristic;

  public Reagent(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type, List<Characteristic> characteristics)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.hasCharacteristic = Collections.unmodifiableList(characteristics);
  }
}
