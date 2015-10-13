package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class StudyFactor extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement description;
  private final Optional<FactorValue> hasFactorValue;

  public StudyFactor(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement description, Optional<FactorValue> factorValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.hasFactorValue = factorValue;
  }
}
