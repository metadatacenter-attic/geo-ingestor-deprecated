package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.BooleanValueElement;
import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.List;
import java.util.Optional;

public class StudyTime extends MetadataTemplateElement
{
  private final StringValueElement durationValue;
  private final BooleanValueElement isBeforeEvent;
  private final StringValueElement studyEvent;
  private final StringValueElement units;

  public StudyTime(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement durationValue,
      BooleanValueElement isBeforeEvent, StringValueElement studyEvent, StringValueElement units)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.durationValue = durationValue;
    this.isBeforeEvent = isBeforeEvent;
    this.studyEvent = studyEvent;
    this.units = units;
  }
}
