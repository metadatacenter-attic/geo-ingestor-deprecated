package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudySubject extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "StudySubject");

  private final StringTemplateFieldInstance name;
  private final StringTemplateFieldInstance type;
  private final List<Characteristic> characteristic;

  public StudySubject(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    StringTemplateFieldInstance type, List<Characteristic> characteristic)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.characteristic = Collections.unmodifiableList(characteristic);
  }

  public StudySubject(StringTemplateFieldInstance name, StringTemplateFieldInstance type,
    List<Characteristic> characteristic)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.type = type;
    this.characteristic = Collections.unmodifiableList(characteristic);
  }
}
