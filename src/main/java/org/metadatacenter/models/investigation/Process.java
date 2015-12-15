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
  private final List<StudyProtocol> executeStudyProtocol;
  private final List<ParameterValue> parameterValue;
  private final List<Input> input;
  private final List<Output> output;

  public Process(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement type,
    List<StudyAssay> studyAssay, List<StudyProtocol> executeStudyProtocol, List<ParameterValue> parameterValues,
    List<Input> inputs, List<Output> outputs)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.studyAssay = Collections.unmodifiableList(studyAssay);
    this.executeStudyProtocol = Collections.unmodifiableList(executeStudyProtocol);
    this.parameterValue = Collections.unmodifiableList(parameterValues);
    this.input = Collections.unmodifiableList(inputs);
    this.output = Collections.unmodifiableList(outputs);
  }

  public Process(StringValueElement type, List<StudyAssay> studyAssay, List<StudyProtocol> executeStudyProtocol,
    List<ParameterValue> parameterValues, List<Input> inputs, List<Output> outputs)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.type = type;
    this.studyAssay = studyAssay;
    this.executeStudyProtocol = executeStudyProtocol;
    this.parameterValue = Collections.unmodifiableList(parameterValues);
    this.input = Collections.unmodifiableList(inputs);
    this.output = Collections.unmodifiableList(outputs);
  }
}
