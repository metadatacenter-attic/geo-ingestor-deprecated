package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public class BooleanTemplateFieldInstance extends TemplateFieldInstance
{
  public BooleanTemplateFieldInstance(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, Boolean value)
  {
    super(jsonLDTypes, jsonLDIdentifier, value.toString());
  }
}
