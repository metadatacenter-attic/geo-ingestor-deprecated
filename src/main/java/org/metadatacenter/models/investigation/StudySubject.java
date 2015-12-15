package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudySubject extends MetadataTemplateElement implements Input
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "StudySubject");

  private final StringValueElement name;
  private final StringValueElement type;
  private final List<Characteristic> characteristic;

  public StudySubject(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type, List<Characteristic> characteristic)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.characteristic = Collections.unmodifiableList(characteristic);
  }

  public StudySubject(StringValueElement name, StringValueElement type, List<Characteristic> characteristic)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.type = type;
    this.characteristic = Collections.unmodifiableList(characteristic);
  }
}
