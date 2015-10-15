package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.DateValueElement;
import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;
import org.metadatacenter.repository.model.URIValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Study extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.URI_BASE + "Study");

  private final StringValueElement title;
  private final StringValueElement description;
  private final StringValueElement identifier;
  private final Optional<DateValueElement> submissionDate;
  private final Optional<DateValueElement> publicReleaseDate;
  private final Optional<URIValueElement> studyDesignType;
  private final List<Process> hasProcess;
  private final Optional<StudyProtocol> hasStudyProtocol;
  private final List<StudyAssay> hasStudyAssay;
  private final List<StudyFactor> hasStudyFactor;
  private final Optional<StudyGroupPopulation> hasStudyGroupPopulation;
  private final List<Publication> hasPublication;
  private final List<Contact> hasContact;

  public Study(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement title,
    StringValueElement description, StringValueElement identifier, Optional<DateValueElement> submissionDate,
    Optional<DateValueElement> publicReleaseDate, Optional<URIValueElement> studyDesignType, List<Process> processes,
    Optional<StudyProtocol> studyProtocol, List<StudyAssay> studyAssays, List<StudyFactor> studyFactors,
    Optional<StudyGroupPopulation> studyGroupPopulation, List<Publication> publications, List<Contact> contacts)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.studyDesignType = studyDesignType;
    this.hasProcess = Collections.unmodifiableList(processes);
    this.hasStudyProtocol = studyProtocol;
    this.hasStudyAssay = Collections.unmodifiableList(studyAssays);
    this.hasStudyFactor = Collections.unmodifiableList(studyFactors);
    this.hasStudyGroupPopulation = studyGroupPopulation;
    this.hasPublication = Collections.unmodifiableList(publications);
    this.hasContact = Collections.unmodifiableList(contacts);
  }

  public Study(StringValueElement title, StringValueElement description, StringValueElement identifier,
    Optional<DateValueElement> submissionDate, Optional<DateValueElement> publicReleaseDate,
    Optional<URIValueElement> studyDesignType, List<Process> processes, Optional<StudyProtocol> studyProtocol,
    List<StudyAssay> studyAssays, List<StudyFactor> studyFactors, Optional<StudyGroupPopulation> studyGroupPopulation,
    List<Publication> publications, List<Contact> contacts)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.studyDesignType = studyDesignType;
    this.hasProcess = Collections.unmodifiableList(processes);
    this.hasStudyProtocol = studyProtocol;
    this.hasStudyAssay = Collections.unmodifiableList(studyAssays);
    this.hasStudyFactor = Collections.unmodifiableList(studyFactors);
    this.hasStudyGroupPopulation = studyGroupPopulation;
    this.hasPublication = Collections.unmodifiableList(publications);
    this.hasContact = Collections.unmodifiableList(contacts);
  }
}
