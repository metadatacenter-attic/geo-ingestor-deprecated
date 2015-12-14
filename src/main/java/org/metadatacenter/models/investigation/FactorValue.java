package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FactorValue extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "FactorValue");

  private final StringValueElement value;
  private final StringValueElement type;
  private final Optional<StringValueElement> unit;
  private final Optional<StudyFactor> hasStudyFactor;

  public FactorValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement value,
    StringValueElement type, Optional<StringValueElement> unit, Optional<StudyFactor> studyFactor)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.hasStudyFactor = studyFactor;
  }

  public FactorValue(StringValueElement value, StringValueElement type, Optional<StringValueElement> unit,
    Optional<StudyFactor> studyFactor)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.type = type;
    this.unit = unit;
    this.value = value;
    this.hasStudyFactor = studyFactor;
  }
}
