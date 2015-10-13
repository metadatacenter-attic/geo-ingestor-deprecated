package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;
import org.metadatacenter.model.repository.URIValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyProtocol extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement description;
  private final StringValueElement type;
  private final URIValueElement uri;
  private final StringValueElement version;
  private final List<ProtocolParameter> hasProtocolParameter;

  public StudyProtocol(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
      StringValueElement description, StringValueElement type, URIValueElement uri, StringValueElement version,
    List<ProtocolParameter> protocolParameters)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.type = type;
    this.uri = uri;
    this.version = version;
    this.hasProtocolParameter = Collections.unmodifiableList(protocolParameters);
  }
}
