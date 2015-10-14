package org.metadatacenter.repository.model;

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

  protected MetadataTemplateElement()
  {
    this.jsonLDTypes = Collections.emptyList();
    this.jsonLDIdentifier = Optional.empty();
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
