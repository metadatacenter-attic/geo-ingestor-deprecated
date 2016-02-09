package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Result extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Result");

  private final StringTemplateFieldInstance name;
  private final Optional<StringTemplateFieldInstance> description;
  private final Optional<ResultValue> resultValue;

  public Result(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    Optional<StringTemplateFieldInstance> description, final Optional<ResultValue> resultValue)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.resultValue = resultValue;
  }

  public Result(StringTemplateFieldInstance name, Optional<StringTemplateFieldInstance> description,
    final Optional<ResultValue> resultValue)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = description;
    this.resultValue = resultValue;
  }

  public Result(StringTemplateFieldInstance name)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = Optional.empty();
    this.resultValue = Optional.empty();
  }
}
