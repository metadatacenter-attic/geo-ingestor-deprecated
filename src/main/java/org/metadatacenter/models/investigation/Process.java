package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Process extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Process");

  private final StringTemplateFieldInstance type;
  private final List<StudyAssay> studyAssay;
  private final List<StudyProtocol> studyProtocol;
  private final List<ParameterValue> parameterValue;
  private final Optional<Input> input;
  private final Optional<Output> output;

  public Process(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance type,
    List<StudyAssay> studyAssay, List<StudyProtocol> studyProtocol, List<ParameterValue> parameterValues,
    Optional<Input> input, Optional<Output> output)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.studyAssay = Collections.unmodifiableList(studyAssay);
    this.studyProtocol = Collections.unmodifiableList(studyProtocol);
    this.parameterValue = Collections.unmodifiableList(parameterValues);
    this.input = input;
    this.output = output;
  }

  public Process(StringTemplateFieldInstance type, List<StudyAssay> studyAssay, List<StudyProtocol> studyProtocol,
    List<ParameterValue> parameterValues, Optional<Input> input, Optional<Output> output)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.type = type;
    this.studyAssay = studyAssay;
    this.studyProtocol = studyProtocol;
    this.parameterValue = Collections.unmodifiableList(parameterValues);
    this.input = input;
    this.output = output;
  }
}
