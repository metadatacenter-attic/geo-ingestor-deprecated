package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class StudySubject extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement type;

  public StudySubject(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement type)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
  }
}
