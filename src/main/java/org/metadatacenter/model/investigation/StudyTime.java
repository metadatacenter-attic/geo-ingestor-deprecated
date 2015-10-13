package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.BooleanValueElement;
import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

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
