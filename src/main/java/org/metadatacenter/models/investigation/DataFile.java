package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DataFile extends MetadataTemplateElement implements Input, Output
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.URI_BASE + "DataFile");

  private final StringValueElement name;
  private final Optional<StringValueElement> description;

  public DataFile(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    Optional<StringValueElement> description)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
  }

  public DataFile(StringValueElement name, Optional<StringValueElement> description)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.name = name;
    this.description = description;
  }
}
