package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyGroupPopulation extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "StudyGroupPopulation");

  private final StringTemplateFieldInstance name;
  private final Optional<StringTemplateFieldInstance> type;
  private final Optional<StringTemplateFieldInstance> selectionRule;
  private final List<StudySubject> studySubject;

  public StudyGroupPopulation(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    StringTemplateFieldInstance name, Optional<StringTemplateFieldInstance> type,
    Optional<StringTemplateFieldInstance> selectionRule, List<StudySubject> studySubjects)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.selectionRule = selectionRule;
    this.studySubject = Collections.unmodifiableList(studySubjects);
  }

  public StudyGroupPopulation(StringTemplateFieldInstance name, Optional<StringTemplateFieldInstance> type,
    Optional<StringTemplateFieldInstance> selectionRule, List<StudySubject> studySubjects)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.type = type;
    this.selectionRule = selectionRule;
    this.studySubject = Collections.unmodifiableList(studySubjects);
  }
}
