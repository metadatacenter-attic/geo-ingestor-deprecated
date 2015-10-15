package org.metadatacenter.repository.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class MetadataTemplateElement
{
  public final List<String> jsonLDTypes;
  public final Optional<String> jsonLDIdentifier;

  protected MetadataTemplateElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier)
  {
    this.jsonLDTypes = Collections.unmodifiableList(jsonLDTypes);
    this.jsonLDIdentifier = jsonLDIdentifier;
  }

  protected MetadataTemplateElement(List<String> jsonLDTypes)
  {
    this.jsonLDTypes = Collections.unmodifiableList(jsonLDTypes);
    this.jsonLDIdentifier = Optional.empty();
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

  protected static Optional<String> generateJSONLDIdentifier(String base)
  {
    return Optional.of(base + UUID.randomUUID().toString());
  }
}
