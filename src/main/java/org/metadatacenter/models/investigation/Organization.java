package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Organization extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "Organization");

  private final StringValueElement name;
  private final Optional<StringValueElement> department;

  public Organization(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    Optional<StringValueElement> department)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.department = department;
  }

  public Organization(StringValueElement name, Optional<StringValueElement> department)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.department = department;
  }
}
