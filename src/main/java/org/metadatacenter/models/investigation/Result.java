package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.List;
import java.util.Optional;

public class Result extends MetadataTemplateElement implements Input, Output
{
  private final StringValueElement name;
  private final StringValueElement description;
  private final Optional<ResultValue> hasResultValue;

  public Result(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement description, final Optional<ResultValue> resultValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.hasResultValue = resultValue;
  }
}
