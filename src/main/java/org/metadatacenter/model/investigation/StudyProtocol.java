package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;
import org.metadatacenter.model.repository.URIValueElement;

import java.util.List;
import java.util.Optional;

public class StudyProtocol extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement department;
  private final StringValueElement type;
  private final URIValueElement uri;
  private final StringValueElement version;

  public StudyProtocol(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement department, StringValueElement type, URIValueElement uri, StringValueElement version)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.department = department;
    this.type = type;
    this.uri = uri;
    this.version = version;
  }
}
