package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyGroupPopulation extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement type;
  private final StringValueElement selectionRule;
  private final List<StudySubject> hasStudySubject;

  public StudyGroupPopulation(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type, StringValueElement selectionRule, List<StudySubject> studySubjects)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.selectionRule = selectionRule;
    this.hasStudySubject = Collections.unmodifiableList(studySubjects);
  }
}
