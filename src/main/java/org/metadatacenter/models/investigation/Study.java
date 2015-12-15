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
  public static final List<String> ElementURIs = Collections
    .singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE + "Study");

  private final StringValueElement title;
  private final StringValueElement description;
  private final StringValueElement identifier;
  private final Optional<DateValueElement> submissionDate;
  private final Optional<DateValueElement> publicReleaseDate;
  private final Optional<URIValueElement> studyDesignType;
  private final List<Process> process;
  private final List<StudyProtocol> studyProtocol;
  private final List<StudyAssay> studyAssay;
  private final List<StudyFactor> studyFactor;
  private final Optional<StudyGroupPopulation> studyGroupPopulation;
  private final List<Publication> publication;
  private final List<Contact> contact;

  public Study(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement title,
    StringValueElement description, StringValueElement identifier, Optional<DateValueElement> submissionDate,
    Optional<DateValueElement> publicReleaseDate, Optional<URIValueElement> studyDesignType, List<Process> processes,
    List<StudyProtocol> studyProtocol, List<StudyAssay> studyAssays, List<StudyFactor> studyFactors,
    Optional<StudyGroupPopulation> studyGroupPopulation, List<Publication> publications, List<Contact> contacts)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.studyDesignType = studyDesignType;
    this.process = Collections.unmodifiableList(processes);
    this.studyProtocol = studyProtocol;
    this.studyAssay = Collections.unmodifiableList(studyAssays);
    this.studyFactor = Collections.unmodifiableList(studyFactors);
    this.studyGroupPopulation = studyGroupPopulation;
    this.publication = Collections.unmodifiableList(publications);
    this.contact = Collections.unmodifiableList(contacts);
  }

  public Study(StringValueElement title, StringValueElement description, StringValueElement identifier,
    Optional<DateValueElement> submissionDate, Optional<DateValueElement> publicReleaseDate,
    Optional<URIValueElement> studyDesignType, List<Process> processes, List<StudyProtocol> studyProtocol,
    List<StudyAssay> studyAssays, List<StudyFactor> studyFactors, Optional<StudyGroupPopulation> studyGroupPopulation,
    List<Publication> publications, List<Contact> contacts)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.studyDesignType = studyDesignType;
    this.process = Collections.unmodifiableList(processes);
    this.studyProtocol = studyProtocol;
    this.studyAssay = Collections.unmodifiableList(studyAssays);
    this.studyFactor = Collections.unmodifiableList(studyFactors);
    this.studyGroupPopulation = studyGroupPopulation;
    this.publication = Collections.unmodifiableList(publications);
    this.contact = Collections.unmodifiableList(contacts);
  }
}
