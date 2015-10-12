package org.metadatacenter.model.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class MetadataTemplateElement
{
  public final List<String> jsonLDTypes;
  public final Optional<String> jsonLDIdentifier;

  protected MetadataTemplateElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier)
  {
    this.jsonLDTypes = jsonLDTypes;
    this.jsonLDIdentifier = jsonLDIdentifier;
  }

  public List<String> getJSONLDTypes()
  {
    return Collections.unmodifiableList(jsonLDTypes);
  }

  public Optional<String> getJSONLDIdentifier()
  {
    return jsonLDIdentifier;
  }
}
