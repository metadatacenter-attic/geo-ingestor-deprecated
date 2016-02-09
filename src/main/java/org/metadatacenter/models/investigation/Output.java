package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;

import java.util.Collections;
import java.util.List;

public class Output extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Output");

  private final List<Result> result;
  private final List<DataFile> dataFile;
  private final List<Sample> sample;

  public Output(List<Result> result, List<DataFile> dataFile, List<Sample> sample)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.result = result;
    this.dataFile = dataFile;
    this.sample = sample;
  }
}