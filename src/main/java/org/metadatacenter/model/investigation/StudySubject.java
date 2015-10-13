package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudySubject extends MetadataTemplateElement implements Input
{
  private final StringValueElement name;
  private final StringValueElement type;
  private final List<Characteristic> hasCharacteristic;

  public StudySubject(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type, List<Characteristic> characteristics)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.hasCharacteristic = Collections.unmodifiableList(characteristics);
  }
}
