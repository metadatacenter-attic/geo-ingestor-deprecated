package org.metadatacenter.ingestors.geo;

import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.ingestors.geo.metadata.Protocol;
import org.metadatacenter.ingestors.geo.metadata.Sample;
import org.metadatacenter.ingestors.geo.metadata.Series;
import org.metadatacenter.models.investigation.Contact;
import org.metadatacenter.models.investigation.Input;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.models.investigation.Output;
import org.metadatacenter.models.investigation.ParameterValue;
import org.metadatacenter.models.investigation.Process;
import org.metadatacenter.models.investigation.ProtocolParameter;
import org.metadatacenter.models.investigation.Publication;
import org.metadatacenter.models.investigation.Study;
import org.metadatacenter.models.investigation.StudyAssay;
import org.metadatacenter.models.investigation.StudyFactor;
import org.metadatacenter.models.investigation.StudyGroupPopulation;
import org.metadatacenter.models.investigation.StudyProtocol;
import org.metadatacenter.repository.model.DateValueElement;
import org.metadatacenter.repository.model.StringValueElement;
import org.metadatacenter.repository.model.URIValueElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.metadatacenter.repository.model.RepositoryFactory.createOptionalStringValueElement;
import static org.metadatacenter.repository.model.RepositoryFactory.createStringValueElement;

public class GEOMetadata2InvestigationConverter
{
  private final GEOMetadata geoMetadata;

  private static final String INVESTIGATION_TEMPLATE_ID = "Investigation";

  public GEOMetadata2InvestigationConverter(GEOMetadata geoMetadata)
  {
    this.geoMetadata = geoMetadata;
  }

  // TODO GEO variables and repeat fields
  public Investigation convert()
  {
    Series geoSeries = this.geoMetadata.getSeries();

    List<String> jsonLDTypes = new ArrayList<>(); // TODO
    Optional<String> jsonLDIdentifier = generateJSONLDIdentifier();
    String templateID = INVESTIGATION_TEMPLATE_ID;
    StringValueElement title = createStringValueElement(geoSeries.getTitle());
    StringValueElement description = createStringValueElement(geoSeries.getSummary());
    StringValueElement identifier = createStringValueElement(geoSeries.getTitle());
    Optional<DateValueElement> submissionDate = Optional.empty();
    Optional<DateValueElement> publicReleaseDate = Optional.empty();
    Optional<StudyProtocol> studyProtocol = convertGEOProtocol2StudyProtocol(this.geoMetadata.getProtocol());
    Study study = convertGEOSeries2Study(this.geoMetadata.getSeries(), this.geoMetadata.getSamples());

    return new Investigation(jsonLDTypes, jsonLDIdentifier, templateID, title, description, identifier, submissionDate,
      publicReleaseDate, Collections.singletonList(study));

  }

  private Study convertGEOSeries2Study(Series series, Map<String, Sample> samples)
  {
    List<String> jsonLDTypes = new ArrayList<>(); // TODO
    Optional<String> jsonLDIdentifier = generateJSONLDIdentifier();
    StringValueElement title = createStringValueElement(series.getTitle());
    StringValueElement description = createStringValueElement(series.getSummary());
    StringValueElement identifier = createStringValueElement(series.getTitle());
    Optional<DateValueElement> submissionDate = Optional.empty();
    Optional<DateValueElement> publicReleaseDate = Optional.empty();
    Optional<URIValueElement> studyDesignType = Optional.empty();
    List<Process> processes = convertGEOSamples2Processes(samples);
    Optional<StudyProtocol> studyProtocol = convertGEOProtocol2StudyProtocol(geoMetadata.getProtocol());
    List<StudyAssay> studyAssays = new ArrayList<>(); // platform // TODO
    List<StudyFactor> studyFactors = new ArrayList<>(); // TODO
    Optional<StudyGroupPopulation> studyGroupPopulation = Optional.empty(); // TODO
    List<Publication> publications = new ArrayList<>(); // TODO
    List<Contact> contacts = new ArrayList<>(); // TODO

    return new Study(jsonLDTypes, jsonLDIdentifier, title, description, identifier, submissionDate, publicReleaseDate,
      studyDesignType, processes, studyProtocol, studyAssays, studyFactors, studyGroupPopulation, publications,
      contacts);

  }

  private List<Process> convertGEOSamples2Processes(Map<String, Sample> geoSamples)
  {
    List<Process> processes = new ArrayList<>();

    for (String sampleName : geoSamples.keySet()) {
      Process process = convertGEOSample2Process(geoSamples.get(sampleName));
      processes.add(process);
    }
    return processes;
  }

  private Process convertGEOSample2Process(Sample sample)
  {
    List<String> jsonLDTypes = new ArrayList<>(); // TODO
    Optional<String> jsonLDIdentifier = generateJSONLDIdentifier();
    StringValueElement type = createStringValueElement("GEOSampleProcess");
    Optional<StudyAssay> hasStudyAssay = Optional.empty(); // TODO
    Optional<StudyProtocol> executeStudyProtocol = Optional.empty(); // TODO
    List<ParameterValue> hasParameterValue = new ArrayList<>(); // TODO
    List<Input> hasInput = new ArrayList<>(); // TODO
    List<Output> hasOutput = new ArrayList<>(); // TODO

    return new Process(jsonLDTypes, jsonLDIdentifier, type, hasStudyAssay, executeStudyProtocol, hasParameterValue,
      hasInput, hasOutput);

  }
  
  private Optional<StudyProtocol> convertGEOProtocol2StudyProtocol(Protocol geoProtocol)
  {
    StringValueElement name = createStringValueElement(geoProtocol.getLabel());
    StringValueElement description = createStringValueElement(geoProtocol.getValueDefinition());
    Optional<StringValueElement> type = Optional.empty();
    Optional<URIValueElement> uri = Optional.empty();
    Optional<StringValueElement> version = Optional.empty();
    List<ProtocolParameter> protocolParameters = new ArrayList<>();

    if (geoProtocol.getGrowth().isPresent())
      protocolParameters.add(createProtocolParameter("growth", geoProtocol.getGrowth().get()));

    if (geoProtocol.getTreatment().isPresent())
      protocolParameters.add(createProtocolParameter("treatment", geoProtocol.getTreatment().get()));

    protocolParameters.add(createProtocolParameter("extract", geoProtocol.getExtract()));
    protocolParameters.add(createProtocolParameter("label", geoProtocol.getLabel()));
    protocolParameters.add(createProtocolParameter("hyb", geoProtocol.getHyb()));
    protocolParameters.add(createProtocolParameter("scan", geoProtocol.getScan()));
    protocolParameters.add(createProtocolParameter("dataProcessing", geoProtocol.getDataProcessing()));
    protocolParameters.add(createProtocolParameter("valueDefinition", geoProtocol.getValueDefinition()));

    return Optional.of(new StudyProtocol(name, description, type, uri, version, protocolParameters));
  }

  private ProtocolParameter createProtocolParameter(String parameterName, String value)
  {
    return createProtocolParameter(parameterName, Optional.empty(), value, Optional.empty(), Optional.empty());
  }

  private ProtocolParameter createProtocolParameter(String parameterName, Optional<String> parameterDescription,
    String value, Optional<String> type, Optional<String> unit)
  {
    ParameterValue parameterValue = new ParameterValue(createStringValueElement(value),
      createOptionalStringValueElement(type), createOptionalStringValueElement(unit));

    return new ProtocolParameter(createStringValueElement(parameterName),
      createOptionalStringValueElement(parameterDescription), Optional.of(parameterValue));
  }

  private List<Publication> convertPubmedIDs2Publications(List<String> pubmedIDs)
  {
    List<Publication> publications = new ArrayList<>();

    for (String pubmedID : pubmedIDs) {
      StringValueElement pubmedIDValueElement = createStringValueElement(pubmedID);
      Publication publication = new Publication(pubmedIDValueElement);
      publications.add(publication);
    }

    return publications;
  }

  private Optional<String> generateJSONLDIdentifier()
  {
    return Optional.of(UUID.randomUUID().toString());
  }
}
