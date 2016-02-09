package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;

import java.util.Collections;
import java.util.List;

public class Input extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Input");

  private final List<Result> result;
  private final List<DataFile> dataFile;
  private final List<Sample> sample;
  private final List<Reagent> reagent;
  private final List<StudySubject> studySubject;

  public Input(List<Result> result, List<DataFile> dataFile, List<Sample> sample, List<Reagent> reagent,
    List<StudySubject> studySubject)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.result = result;
    this.dataFile = dataFile;
    this.sample = sample;
    this.reagent = reagent;
    this.studySubject = studySubject;
  }
}