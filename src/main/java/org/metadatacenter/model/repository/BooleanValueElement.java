package org.metadatacenter.model.repository;

import java.util.List;
import java.util.Optional;

public class BooleanValueElement extends ValueElement
{
  public BooleanValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, Boolean value)
  {
    super(jsonLDTypes, jsonLDIdentifier, value.toString());
  }
}
