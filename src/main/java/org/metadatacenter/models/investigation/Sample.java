package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Sample extends MetadataTemplateElement implements Input, Output
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.URI_BASE + "Sample");

  private final StringValueElement name;
  private final StringValueElement type;
  private final Optional<StringValueElement> description;
  private final Optional<StringValueElement> source;
  private final List<Characteristic> hasCharacteristic;
  private final Optional<StudyTime> hasStudyTime;

  public Sample(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type, Optional<StringValueElement> description, Optional<StringValueElement> source,
    List<Characteristic> characteristics, Optional<StudyTime> studyTime)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
    this.hasCharacteristic = Collections.unmodifiableList(characteristics);
    this.hasStudyTime = studyTime;
  }

  public Sample(StringValueElement name, StringValueElement type, Optional<StringValueElement> description,
    Optional<StringValueElement> source, List<Characteristic> characteristics, Optional<StudyTime> studyTime)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
    this.hasCharacteristic = Collections.unmodifiableList(characteristics);
    this.hasStudyTime = studyTime;
  }
}
