package org.metadatacenter.repository.model;

import io.gsonfire.annotations.ExcludeByValue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Using GSON Fire directives we exclude the {@link #jsonLDTypes} and {@link #jsonLDIdentifier} fields from direct
 * serialization and use the {@link MetadataTemplateElementPostProcessor} class to serialize them as JSON-LD-conforming
 * <tt>@type</tt> and <tt>@id</tt> fields.
 * </p>
 * Note that the corresponding deserialization logic has not been implemented.
 *
 * @see ExcludeJSONLDTypesLogic
 * @see ExcludeJSONLDIdentifierLogic
 * @see MetadataTemplateElementPostProcessor
 * @see MetadataTemplateJSONSerializer
 */
public abstract class MetadataTemplateElement
{
  @ExcludeByValue(ExcludeJSONLDContextLogic.class) public final Optional<JSONLDContext> jsonLDContext;

  @ExcludeByValue(ExcludeJSONLDTypesLogic.class) public final List<String> jsonLDTypes;

  @ExcludeByValue(ExcludeJSONLDIdentifierLogic.class) public final Optional<String> jsonLDIdentifier;

  protected MetadataTemplateElement(Optional<JSONLDContext> jsonLDContext, List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier)
  {
    this.jsonLDContext = jsonLDContext;
    this.jsonLDTypes = Collections.unmodifiableList(jsonLDTypes);
    this.jsonLDIdentifier = jsonLDIdentifier;
  }

  protected MetadataTemplateElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier)
  {
    this.jsonLDContext = Optional.empty();
    this.jsonLDTypes = Collections.unmodifiableList(jsonLDTypes);
    this.jsonLDIdentifier = jsonLDIdentifier;
  }

  protected MetadataTemplateElement(List<String> jsonLDTypes)
  {
    this.jsonLDContext = Optional.empty();
    this.jsonLDTypes = Collections.unmodifiableList(jsonLDTypes);
    this.jsonLDIdentifier = Optional.empty();
  }

  protected MetadataTemplateElement()
  {
    this.jsonLDContext = Optional.empty();
    this.jsonLDTypes = Collections.emptyList();
    this.jsonLDIdentifier = Optional.empty();
  }

  public Optional<JSONLDContext> getJSONLDContext()
  {
    return this.jsonLDContext;
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
