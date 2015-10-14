package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Process extends MetadataTemplateElement
{
  private final StringValueElement type;
  private final Optional<StudyAssay> hasStudyAssay;
  private final Optional<StudyProtocol> executeStudyProtocol;
  private final List<ParameterValue> hasParameterValue;
  private final List<Input> hasInput;
  private final List<Output> hasOutput;

  public Process(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement type,
    Optional<StudyAssay> hasStudyAssay, Optional<StudyProtocol> executeStudyProtocol,
    List<ParameterValue> parameterValues, List<Input> inputs, List<Output> outputs)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
    this.hasStudyAssay = hasStudyAssay;
    this.executeStudyProtocol = executeStudyProtocol;
    this.hasParameterValue = Collections.unmodifiableList(parameterValues);
    this.hasInput = Collections.unmodifiableList(inputs);
    this.hasOutput = Collections.unmodifiableList(outputs);
  }
}
