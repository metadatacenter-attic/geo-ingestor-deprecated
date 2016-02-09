package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Organization extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Organization");

  private final StringTemplateFieldInstance name;
  private final Optional<StringTemplateFieldInstance> department;

  public Organization(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    Optional<StringTemplateFieldInstance> department)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.department = department;
  }

  public Organization(StringTemplateFieldInstance name, Optional<StringTemplateFieldInstance> department)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.department = department;
  }
}
