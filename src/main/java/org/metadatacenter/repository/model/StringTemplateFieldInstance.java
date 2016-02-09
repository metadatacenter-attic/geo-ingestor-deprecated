package org.metadatacenter.repository.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StringTemplateFieldInstance extends TemplateFieldInstance
{
  public StringTemplateFieldInstance(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String value)
  {
    super(jsonLDTypes, jsonLDIdentifier, value);
  }

  public StringTemplateFieldInstance(String value)
  {
    super(Collections.emptyList(), Optional.empty(), value);
  }
}
