package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Publication extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Publication");

  private final Optional<StringTemplateFieldInstance> title;
  private final Optional<StringTemplateFieldInstance> pubMedID;
  private final Optional<StringTemplateFieldInstance> doi;
  private final Optional<StringTemplateFieldInstance> status;
  private final List<StringTemplateFieldInstance> authorList;

  public Publication(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    Optional<StringTemplateFieldInstance> title, Optional<StringTemplateFieldInstance> pubMedID,
    Optional<StringTemplateFieldInstance> doi, Optional<StringTemplateFieldInstance> status,
    List<StringTemplateFieldInstance> authorList)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.pubMedID = pubMedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }

  public Publication(Optional<StringTemplateFieldInstance> title, Optional<StringTemplateFieldInstance> pubMedID,
    Optional<StringTemplateFieldInstance> doi, Optional<StringTemplateFieldInstance> status,
    List<StringTemplateFieldInstance> authorList)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.title = title;
    this.pubMedID = pubMedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }

  public Publication(StringTemplateFieldInstance pubMedID)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.title = Optional.empty();
    this.pubMedID = Optional.of(pubMedID);
    this.doi = Optional.empty();
    this.status = Optional.empty();
    this.authorList = Collections.emptyList();
  }
}
