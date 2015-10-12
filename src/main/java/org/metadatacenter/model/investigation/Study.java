package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.DateValueElement;
import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.StringValueElement;
import org.metadatacenter.model.repository.URIValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Study extends MetadataTemplateElement
{
  private final StringValueElement title;
  private final StringValueElement description;
  private final StringValueElement identifier;
  private final Optional<DateValueElement> submissionDate;
  private final Optional<DateValueElement> publicReleaseDate;
  private final Optional<URIValueElement> studyDesignType;
  private final List<Process> hasProcess;
  private final List<StudyAssay> hasStudyAssay;
  private final List<StudyGroupPopulation> hasStudyGroupPopulation;
  private final List<Publication> hasPublication;

  public Study(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement title,
    StringValueElement description, StringValueElement identifier, Optional<DateValueElement> submissionDate,
    Optional<DateValueElement> publicReleaseDate, Optional<URIValueElement> studyDesignType, List<Process> processes,
    List<StudyAssay> studyAssays, List<StudyGroupPopulation> studyGroupPopulations, List<Publication> publications)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.studyDesignType = studyDesignType;
    this.hasProcess = Collections.unmodifiableList(processes);
    this.hasStudyAssay = Collections.unmodifiableList(studyAssays);
    this.hasStudyGroupPopulation = Collections.unmodifiableList(studyGroupPopulations);
    this.hasPublication = Collections.unmodifiableList(publications);
  }
}
