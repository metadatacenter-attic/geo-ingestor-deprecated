package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Publication extends MetadataTemplateElement
{
  private final StringValueElement title;
  private final StringValueElement pubmedID;
  private final StringValueElement doi;
  private final StringValueElement status;
  private final List<StringValueElement> authorList;

  public Publication(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement title,
    StringValueElement pubmedID, StringValueElement doi, StringValueElement status, List<StringValueElement> authorList)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.pubmedID = pubmedID;
    this.doi = doi;
    this.status = status;
    this.authorList = Collections.unmodifiableList(authorList);
  }
}
