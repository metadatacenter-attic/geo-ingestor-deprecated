package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyGroupPopulation extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "StudyGroupPopulation");

  private final StringValueElement name;
  private final Optional<StringValueElement> type;
  private final Optional<StringValueElement> selectionRule;
  private final List<StudySubject> hasStudySubject;

  public StudyGroupPopulation(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    Optional<StringValueElement> type, Optional<StringValueElement> selectionRule, List<StudySubject> studySubjects)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.selectionRule = selectionRule;
    this.hasStudySubject = Collections.unmodifiableList(studySubjects);
  }

  public StudyGroupPopulation(StringValueElement name, Optional<StringValueElement> type,
    Optional<StringValueElement> selectionRule, List<StudySubject> studySubjects)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.type = type;
    this.selectionRule = selectionRule;
    this.hasStudySubject = Collections.unmodifiableList(studySubjects);
  }
}
