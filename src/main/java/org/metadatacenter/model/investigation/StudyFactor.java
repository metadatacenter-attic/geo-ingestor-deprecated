package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class StudyFactor extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement description;

  public StudyFactor(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement description)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
  }
}
