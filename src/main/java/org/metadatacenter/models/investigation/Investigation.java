package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.DateValueElement;
import org.metadatacenter.repository.model.MetadataTemplate;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Investigation extends MetadataTemplate
{
  private final StringValueElement title;
  private final StringValueElement description;
  private final StringValueElement identifier;
  private final Optional<DateValueElement> submissionDate;
  private final Optional<DateValueElement> publicReleaseDate;
  private final List<Study> hasStudy;

  public Investigation(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String templateID,
    StringValueElement title, StringValueElement description, StringValueElement identifier,
    Optional<DateValueElement> submissionDate, Optional<DateValueElement> publicReleaseDate,
    List<Study> studies)
  {
    super(jsonLDTypes, jsonLDIdentifier, templateID);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.hasStudy = Collections.unmodifiableList(studies);
  }

  public Investigation(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String templateID,
    StringValueElement title, StringValueElement description, StringValueElement identifier,
    List<Study> studies)
  {
    super(jsonLDTypes, jsonLDIdentifier, templateID);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = Optional.empty();
    this.publicReleaseDate = Optional.empty();
    this.hasStudy = Collections.unmodifiableList(studies);
  }
}
