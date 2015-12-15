package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Publication extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE
    + "Publication");

  private final Optional<StringValueElement> title;
  private final Optional<StringValueElement> pubMedID;
  private final Optional<StringValueElement> doi;
  private final Optional<StringValueElement> status;
  private final List<StringValueElement> authorList;

  public Publication(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, Optional<StringValueElement> title,
    Optional<StringValueElement> pubMedID, Optional<StringValueElement> doi, Optional<StringValueElement> status,
    List<StringValueElement> authorList)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.pubMedID = pubMedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }

  public Publication(Optional<StringValueElement> title, Optional<StringValueElement> pubMedID,
    Optional<StringValueElement> doi, Optional<StringValueElement> status, List<StringValueElement> authorList)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.title = title;
    this.pubMedID = pubMedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }

  public Publication(StringValueElement pubMedID)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.title = Optional.empty();
    this.pubMedID = Optional.of(pubMedID);
    this.doi = Optional.empty();
    this.status = Optional.empty();
    this.authorList = Collections.emptyList();
  }
}
