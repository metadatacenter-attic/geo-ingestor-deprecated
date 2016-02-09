package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.DateTemplateFieldInstance;
import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;
import org.metadatacenter.repository.model.URITemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Study extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Study");

  private final StringTemplateFieldInstance title;
  private final StringTemplateFieldInstance description;
  private final StringTemplateFieldInstance identifier;
  private final Optional<DateTemplateFieldInstance> submissionDate;
  private final Optional<DateTemplateFieldInstance> publicReleaseDate;
  private final Optional<URITemplateFieldInstance> studyDesignType;
  private final List<Process> process;
  private final List<StudyProtocol> studyProtocol;
  private final List<StudyAssay> studyAssay;
  private final List<StudyFactor> studyFactor;
  private final Optional<StudyGroupPopulation> studyGroupPopulation;
  private final List<Publication> publication;
  private final List<Contact> contact;

  public Study(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance title,
    StringTemplateFieldInstance description, StringTemplateFieldInstance identifier,
    Optional<DateTemplateFieldInstance> submissionDate, Optional<DateTemplateFieldInstance> publicReleaseDate,
    Optional<URITemplateFieldInstance> studyDesignType, List<Process> processes, List<StudyProtocol> studyProtocol,
    List<StudyAssay> studyAssays, List<StudyFactor> studyFactors, Optional<StudyGroupPopulation> studyGroupPopulation,
    List<Publication> publications, List<Contact> contacts)
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

  public Study(StringTemplateFieldInstance title, StringTemplateFieldInstance description,
    StringTemplateFieldInstance identifier, Optional<DateTemplateFieldInstance> submissionDate,
    Optional<DateTemplateFieldInstance> publicReleaseDate, Optional<URITemplateFieldInstance> studyDesignType,
    List<Process> processes, List<StudyProtocol> studyProtocol, List<StudyAssay> studyAssays,
    List<StudyFactor> studyFactors, Optional<StudyGroupPopulation> studyGroupPopulation, List<Publication> publications,
    List<Contact> contacts)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
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
