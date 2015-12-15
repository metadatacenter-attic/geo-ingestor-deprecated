package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Sample extends MetadataTemplateElement implements Input, Output
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE
    + "Sample");

  private final StringValueElement name;
  private final StringValueElement type;
  private final Optional<StringValueElement> description;
  private final Optional<StringValueElement> source;
  private final List<Characteristic> characteristic;
  private final Optional<StudyTime> studyTime;

  public Sample(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type, Optional<StringValueElement> description, Optional<StringValueElement> source,
    List<Characteristic> characteristics, Optional<StudyTime> studyTime)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
    this.characteristic = Collections.unmodifiableList(characteristics);
    this.studyTime = studyTime;
  }

  public Sample(StringValueElement name, StringValueElement type, Optional<StringValueElement> description,
    Optional<StringValueElement> source, List<Characteristic> characteristics, Optional<StudyTime> studyTime)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
    this.characteristic = Collections.unmodifiableList(characteristics);
    this.studyTime = studyTime;
  }
}
