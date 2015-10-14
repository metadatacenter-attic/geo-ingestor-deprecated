package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.List;
import java.util.Optional;

public class DataFile extends MetadataTemplateElement implements Input, Output

{
  private final StringValueElement name;
  private final StringValueElement description;

  public DataFile(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement description)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
  }
}
