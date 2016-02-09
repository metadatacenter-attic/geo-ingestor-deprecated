package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FactorValue extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "FactorValue");

  private final StringTemplateFieldInstance value;
  private final StringTemplateFieldInstance type;
  private final Optional<StringTemplateFieldInstance> unit;
  private final Optional<StudyFactor> studyFactor;

  public FactorValue(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance value,
    StringTemplateFieldInstance type, Optional<StringTemplateFieldInstance> unit, Optional<StudyFactor> studyFactor)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
    this.type = type;
    this.unit = unit;
    this.studyFactor = studyFactor;
  }

  public FactorValue(StringTemplateFieldInstance value, StringTemplateFieldInstance type,
    Optional<StringTemplateFieldInstance> unit, Optional<StudyFactor> studyFactor)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.type = type;
    this.unit = unit;
    this.value = value;
    this.studyFactor = studyFactor;
  }
}
