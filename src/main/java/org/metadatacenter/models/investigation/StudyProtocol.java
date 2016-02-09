package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;
import org.metadatacenter.repository.model.URITemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyProtocol extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "StudyProtocol");

  private final StringTemplateFieldInstance name;
  private final StringTemplateFieldInstance description;
  private final Optional<StringTemplateFieldInstance> type;
  private final Optional<URITemplateFieldInstance> uri;
  private final Optional<StringTemplateFieldInstance> version;
  private final List<ProtocolParameter> protocolParameter;

  public StudyProtocol(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance name,
    StringTemplateFieldInstance description, Optional<StringTemplateFieldInstance> type,
    Optional<URITemplateFieldInstance> uri, Optional<StringTemplateFieldInstance> version,
    List<ProtocolParameter> protocolParameters)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.name = name;
    this.description = description;
    this.type = type;
    this.uri = uri;
    this.version = version;
    this.protocolParameter = Collections.unmodifiableList(protocolParameters);
  }

  public StudyProtocol(StringTemplateFieldInstance name, StringTemplateFieldInstance description,
    Optional<StringTemplateFieldInstance> type, Optional<URITemplateFieldInstance> uri,
    Optional<StringTemplateFieldInstance> version, List<ProtocolParameter> protocolParameters)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.name = name;
    this.description = description;
    this.type = type;
    this.uri = uri;
    this.version = version;
    this.protocolParameter = Collections.unmodifiableList(protocolParameters);
  }
}
