package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Sample extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Sample");

  private final StringTemplateFieldInstance name;
  private final StringTemplateFieldInstance type;
  private final Optional<StringTemplateFieldInstance> description;
  private final Optional<StringTemplateFieldInstance> source;
  private final List<Characteristic> characteristic;
  private final Optional<StudyTime> studyTime;

  public Sample(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    StringTemplateFieldInstance type, Optional<StringTemplateFieldInstance> description,
    Optional<StringTemplateFieldInstance> source, List<Characteristic> characteristics, Optional<StudyTime> studyTime)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
    this.characteristic = Collections.unmodifiableList(characteristics);
    this.studyTime = studyTime;
  }

  public Sample(StringTemplateFieldInstance name, StringTemplateFieldInstance type,
    Optional<StringTemplateFieldInstance> description, Optional<StringTemplateFieldInstance> source,
    List<Characteristic> characteristics, Optional<StudyTime> studyTime)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
    this.characteristic = Collections.unmodifiableList(characteristics);
    this.studyTime = studyTime;
  }
}
