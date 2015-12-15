package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Process extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "Process");

  private final StringValueElement type;
  private final List<StudyAssay> studyAssay;
  private final List<StudyProtocol> studyProtocol;
  private final List<ParameterValue> parameterValue;
  private final Optional<Input> input;
  private final Optional<Output> output;

  public Process(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement type,
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

  public Process(StringValueElement type, List<StudyAssay> studyAssay, List<StudyProtocol> studyProtocol,
    List<ParameterValue> parameterValues, Optional<Input> input, Optional<Output> output)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.type = type;
    this.studyAssay = studyAssay;
    this.studyProtocol = studyProtocol;
    this.parameterValue = Collections.unmodifiableList(parameterValues);
    this.input = input;
    this.output = output;
  }
}
