package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Publication extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.URI_BASE + "Publication");

  private final Optional<StringValueElement> title;
  private final Optional<StringValueElement> pubmedID;
  private final Optional<StringValueElement> doi;
  private final Optional<StringValueElement> status;
  private final List<StringValueElement> authorList;

  public Publication(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, Optional<StringValueElement> title,
    Optional<StringValueElement> pubmedID, Optional<StringValueElement> doi, Optional<StringValueElement> status,
    List<StringValueElement> authorList)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.pubmedID = pubmedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }

  public Publication(Optional<StringValueElement> title, Optional<StringValueElement> pubmedID,
    Optional<StringValueElement> doi, Optional<StringValueElement> status, List<StringValueElement> authorList)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.title = title;
    this.pubmedID = pubmedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }

  public Publication(StringValueElement pubmedID)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.title = Optional.empty();
    this.pubmedID = Optional.of(pubmedID);
    this.doi = Optional.empty();
    this.status = Optional.empty();
    this.authorList = Collections.emptyList();
  }
}