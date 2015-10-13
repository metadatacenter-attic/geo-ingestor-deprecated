package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class Sample extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement type;
  private final StringValueElement description;
  private final StringValueElement source;

  public Sample(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement type, StringValueElement description, StringValueElement source)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.type = type;
    this.description = description;
    this.source = source;
  }
}
