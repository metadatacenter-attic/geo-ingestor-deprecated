package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class Process extends MetadataTemplateElement
{
  private final StringValueElement type;

  public Process(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement type)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.type = type;
  }
}
