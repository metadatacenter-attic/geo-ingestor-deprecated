package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.BooleanTemplateFieldInstance;
import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyTime extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "StudyTime");

  private final StringTemplateFieldInstance durationValue;
  private final BooleanTemplateFieldInstance isBeforeEvent;
  private final StringTemplateFieldInstance studyEvent;
  private final StringTemplateFieldInstance units;

  public StudyTime(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    StringTemplateFieldInstance durationValue, BooleanTemplateFieldInstance isBeforeEvent,
    StringTemplateFieldInstance studyEvent, StringTemplateFieldInstance units)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.durationValue = durationValue;
    this.isBeforeEvent = isBeforeEvent;
    this.studyEvent = studyEvent;
    this.units = units;
  }

  public StudyTime(StringTemplateFieldInstance durationValue, BooleanTemplateFieldInstance isBeforeEvent,
    StringTemplateFieldInstance studyEvent, StringTemplateFieldInstance units)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.durationValue = durationValue;
    this.isBeforeEvent = isBeforeEvent;
    this.studyEvent = studyEvent;
    this.units = units;
  }
}
