package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class Organization extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement department;

  public Organization(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement department)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.department = department;
  }
}
