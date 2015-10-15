package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Result extends MetadataTemplateElement implements Input, Output
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.URI_BASE + "Result");

  private final StringValueElement name;
  private final Optional<StringValueElement> description;
  private final Optional<ResultValue> hasResultValue;

  public Result(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    Optional<StringValueElement> description, final Optional<ResultValue> resultValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.hasResultValue = resultValue;
  }

  public Result(StringValueElement name, Optional<StringValueElement> description,
    final Optional<ResultValue> resultValue)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.name = name;
    this.description = description;
    this.hasResultValue = resultValue;
  }

  public Result(StringValueElement name)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.name = name;
    this.description = Optional.empty();
    this.hasResultValue = Optional.empty();
  }
}
