package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;
import org.metadatacenter.repository.model.URIValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyProtocol extends MetadataTemplateElement
{
  private final StringValueElement name;
  private final StringValueElement description;
  private final Optional<StringValueElement> type;
  private final Optional<URIValueElement> uri;
  private final Optional<StringValueElement> version;
  private final List<ProtocolParameter> hasProtocolParameter;

  public StudyProtocol(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement name,
    StringValueElement description, Optional<StringValueElement> type, Optional<URIValueElement> uri,
    Optional<StringValueElement> version, List<ProtocolParameter> protocolParameters)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.type = type;
    this.uri = uri;
    this.version = version;
    this.hasProtocolParameter = Collections.unmodifiableList(protocolParameters);
  }

  public StudyProtocol(StringValueElement name, StringValueElement description, Optional<StringValueElement> type,
    Optional<URIValueElement> uri, Optional<StringValueElement> version, List<ProtocolParameter> protocolParameters)
  {
    super();
    this.name = name;
    this.description = description;
    this.type = type;
    this.uri = uri;
    this.version = version;
    this.hasProtocolParameter = Collections.unmodifiableList(protocolParameters);
  }
}
