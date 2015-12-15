package org.metadatacenter.ingestors.geo;

import org.metadatacenter.ingestors.geo.metadata.Contributor;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.ingestors.geo.metadata.Platform;
import org.metadatacenter.ingestors.geo.metadata.Protocol;
import org.metadatacenter.ingestors.geo.metadata.Sample;
import org.metadatacenter.ingestors.geo.metadata.Series;
import org.metadatacenter.ingestors.geo.soft.GEOSoftNames;
import org.metadatacenter.models.investigation.Characteristic;
import org.metadatacenter.models.investigation.CharacteristicValue;
import org.metadatacenter.models.investigation.Contact;
import org.metadatacenter.models.investigation.DataFile;
import org.metadatacenter.models.investigation.Input;
import org.metadatacenter.models.investigation.Investigation;
import org.metadatacenter.models.investigation.InvestigationNames;
import org.metadatacenter.models.investigation.Organization;
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
import org.metadatacenter.models.investigation.StudyTime;
import org.metadatacenter.repository.model.DateValueElement;
import org.metadatacenter.repository.model.StringValueElement;
import org.metadatacenter.repository.model.URIValueElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.metadatacenter.repository.model.RepositoryFactory.createOptionalEmailValueElement;
import static org.metadatacenter.repository.model.RepositoryFactory.createOptionalPhoneValueElement;
import static org.metadatacenter.repository.model.RepositoryFactory.createOptionalStringValueElement;
import static org.metadatacenter.repository.model.RepositoryFactory.createStringValueElement;

/**
 * Take a {@link GEOSubmissionMetadata} object and convert it to a CEDAR {@link Investigation} object.
 * <p/>
 * TODO variables and repeat fields not currently transferred to CEDAR Investigation model
 *
 * @see GEOSubmissionMetadata
 * @see Investigation
 */
public class GEOSubmissionMetadata2InvestigationConverter
{
  public Investigation convertGEOSubmissionMetadata2Investigation(GEOSubmissionMetadata geoSubmissionMetadata)
  {
    Series geoSeries = geoSubmissionMetadata.getSeries();

    String templateID = InvestigationNames.INVESTIGATION_TEMPLATE_ID;
    StringValueElement title = createStringValueElement(geoSeries.getTitle());
    StringValueElement description = createStringValueElement(concatenateFieldValues(geoSeries.getSummary()));
    StringValueElement identifier = createStringValueElement(geoSeries.getTitle());
    Optional<DateValueElement> submissionDate = Optional.empty();
    Optional<DateValueElement> publicReleaseDate = Optional.empty();
    Optional<StudyProtocol> studyProtocol = convertGEOProtocol2StudyProtocol(geoSubmissionMetadata.getProtocol());
    List<StudyProtocol> studyProtocols = studyProtocol.isPresent() ?
      Collections.singletonList(studyProtocol.get()) :
      Collections.emptyList();
    Study study = convertGEOSeries2Study(geoSubmissionMetadata.getSeries(), geoSubmissionMetadata.getSamples(),
      geoSubmissionMetadata.getPlatforms(), studyProtocols);

    return new Investigation(templateID, title, description, identifier, submissionDate, publicReleaseDate,
      Collections.singletonList(study));

  }

  private Study convertGEOSeries2Study(Series geoSeries, Map<String, Sample> geoSamples, List<Platform> geoPlatforms,
    List<StudyProtocol> studyProtocols)
  {
    StringValueElement title = createStringValueElement(geoSeries.getTitle());
    StringValueElement description = createStringValueElement(concatenateFieldValues(geoSeries.getSummary()));
    StringValueElement identifier = createStringValueElement(geoSeries.getTitle());
    Optional<DateValueElement> submissionDate = Optional.empty();
    Optional<DateValueElement> publicReleaseDate = Optional.empty();
    Optional<URIValueElement> studyDesignType = Optional.empty();
    List<StudyAssay> studyAssays = convertGEOPlatforms2StudyAssays(geoPlatforms);
    List<Process> processes = convertGEOSamples2Processes(geoSamples, studyAssays, studyProtocols);
    List<StudyFactor> studyFactors = new ArrayList<>(); // TODO Not clear where these are in GEO.
    Optional<StudyGroupPopulation> studyGroupPopulation = Optional.empty(); // Not recorded in GEO
    List<Publication> publications = convertPubmedIDs2Publications(geoSeries.getPubmedIDs());
    List<Contact> contacts = convertGEOContributors2Contacts(geoSeries.getContributors());
    Map<String, Map<String, String>> variables = geoSeries.getVariables(); // TODO Where do these go in our model?
    Map<String, List<String>> repeat = geoSeries.getRepeats(); // TODO Where do these go in our model?

    return new Study(title, description, identifier, submissionDate, publicReleaseDate, studyDesignType, processes,
      studyProtocols, studyAssays, studyFactors, studyGroupPopulation, publications, contacts);
  }

  private List<Process> convertGEOSamples2Processes(Map<String, Sample> geoSamples, List<StudyAssay> studyAssays,
    List<StudyProtocol> studyProtocols)
  {
    List<Process> processes = new ArrayList<>();

    for (String sampleName : geoSamples.keySet()) {
      Process process = convertGEOSample2Process(geoSamples.get(sampleName), studyAssays, studyProtocols);
      processes.add(process);
    }
    return processes;
  }

  private Process convertGEOSample2Process(Sample geoSample, List<StudyAssay> studyAssays,
    List<StudyProtocol> studyProtocols)
  {
    StringValueElement type = createStringValueElement("GEOSampleProcess");
    List<ParameterValue> parameterValue = new ArrayList<>(); // Stored via the study protocol
    Optional<StudyAssay> sampleStudyAssay = extractStudyAssayFromGEOSample(geoSample);
    List<StudyAssay> sampleStudyAssays = sampleStudyAssay.isPresent() ?
      Collections.singletonList(sampleStudyAssay.get()) :
      Collections.emptyList();
    org.metadatacenter.models.investigation.Sample sample = extractSampleFromGEOSample(geoSample);
    List<DataFile> dataFiles = extractDataFilesFromGEOSample(geoSample);
    List<Input> input = new ArrayList<>();
    List<Output> output = new ArrayList<>();

    input.add(sample);
    output.addAll(dataFiles);

    return new Process(type, sampleStudyAssays, studyProtocols, parameterValue, input, output);
  }

  private org.metadatacenter.models.investigation.Sample extractSampleFromGEOSample(
    org.metadatacenter.ingestors.geo.metadata.Sample geoSample)
  {
    StringValueElement name = createStringValueElement(geoSample.getGSM());
    StringValueElement type = createStringValueElement(geoSample.getGPL());
    Optional<StringValueElement> description = createOptionalStringValueElement(geoSample.getTitle());
    Optional<StringValueElement> source = createOptionalStringValueElement(geoSample.getBiomaterialProvider());
    List<Characteristic> characteristics = convertGEOCharacteristics2Characteristics(geoSample.getCharacteristics());
    Optional<StudyTime> studyTime = Optional.empty(); // Not present

    return new org.metadatacenter.models.investigation.Sample(name, type, description, source, characteristics,
      studyTime);
  }

  private Optional<StudyAssay> extractStudyAssayFromGEOSample(Sample geoSample)
  {
    return Optional.of(new StudyAssay(createStringValueElement(geoSample.getGPL())));
  }

  private List<StudyAssay> convertGEOPlatforms2StudyAssays(List<Platform> geoPlatforms)
  {
    List<StudyAssay> studyAssays = new ArrayList<>();

    for (Platform geoPlatform : geoPlatforms) {
      StudyAssay studyAssay = convertGEOPlatform2StudyAssay(geoPlatform);
      studyAssays.add(studyAssay);
    }

    return studyAssays;
  }

  private StudyAssay convertGEOPlatform2StudyAssay(Platform geoPlatform)
  {
    return new StudyAssay(createStringValueElement(geoPlatform.getTitle()),
      createOptionalStringValueElement(Optional.of(geoPlatform.getDistribution())),
      createOptionalStringValueElement(Optional.of(geoPlatform.getTechnology())));
  }

  private Optional<StudyProtocol> convertGEOProtocol2StudyProtocol(Optional<Protocol> geoProtocol)
  {
    if (geoProtocol.isPresent()) {
      StringValueElement name = createStringValueElement(
        geoProtocol.get().getLabel().isEmpty() ? "" : concatenateFieldValues(geoProtocol.get().getLabel()));
      StringValueElement description = createStringValueElement(geoProtocol.get().getValueDefinition().isEmpty() ?
        "" :
        concatenateFieldValues(geoProtocol.get().getValueDefinition()));

      Optional<StringValueElement> type = Optional.empty();
      Optional<URIValueElement> uri = Optional.empty();
      Optional<StringValueElement> version = Optional.empty();
      List<ProtocolParameter> protocolParameters = new ArrayList<>();

      if (!geoProtocol.get().getGrowth().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_GROWTH_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getGrowth())));

      if (!geoProtocol.get().getTreatment().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_TREATMENT_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getTreatment())));

      if (!geoProtocol.get().getExtract().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_EXTRACT_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getExtract())));

      if (!geoProtocol.get().getLabel().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_LABEL_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getLabel())));

      if (!geoProtocol.get().getHyb().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_HYB_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getHyb())));

      if (!geoProtocol.get().getScan().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_SCAN_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getScan())));

      if (!geoProtocol.get().getDataProcessing().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_DATA_PROCESSING_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getDataProcessing())));

      if (!geoProtocol.get().getValueDefinition().isEmpty())
        protocolParameters.add(createProtocolParameter(GEOSoftNames.PROTOCOL_VALUE_DEFINITION_FIELD_NAME,
          concatenateFieldValues(geoProtocol.get().getValueDefinition())));

      for (String fieldName : geoProtocol.get().getUserDefinedFields().keySet())
        protocolParameters.add(createProtocolParameter(fieldName,
          concatenateFieldValues(geoProtocol.get().getUserDefinedFields().get(fieldName))));

      return Optional.of(new StudyProtocol(name, description, type, uri, version, protocolParameters));
    } else
      return Optional.empty();
  }

  private List<Characteristic> convertGEOCharacteristics2Characteristics(Map<String, String> geoCharacteristics)
  {
    List<Characteristic> characteristics = new ArrayList<>();

    for (String geoCharacteristicName : geoCharacteristics.keySet()) {
      String geoCharacteristicValue = geoCharacteristics.get(geoCharacteristicName);
      CharacteristicValue characteristicValue = new CharacteristicValue(
        createStringValueElement(geoCharacteristicValue));
      Characteristic characteristic = new Characteristic(createStringValueElement(geoCharacteristicName),
        Optional.of(characteristicValue));

      characteristics.add(characteristic);
    }

    return characteristics;
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

  private List<Contact> convertGEOContributors2Contacts(List<Contributor> geoContributors)
  {
    List<Contact> contacts = new ArrayList<>();

    for (Contributor geoContributor : geoContributors) {
      Organization organization = new Organization(createStringValueElement(geoContributor.getInstitute()),
        createOptionalStringValueElement(geoContributor.getDepartment()));
      Contact contact = new Contact(createStringValueElement(""), createStringValueElement(""),
        createStringValueElement(geoContributor.getName()),
        createOptionalStringValueElement(geoContributor.getAddress()),
        createOptionalEmailValueElement(geoContributor.getEmail()),
        createOptionalPhoneValueElement(geoContributor.getPhone()),
        createOptionalPhoneValueElement(geoContributor.getFax()), Optional.empty(), Optional.of(organization));

      contacts.add(contact);
    }
    return contacts;
  }

  private List<DataFile> extractDataFilesFromGEOSample(org.metadatacenter.ingestors.geo.metadata.Sample geoSample)
  {
    List<DataFile> dataFiles = new ArrayList<>();

    for (String rawDataFile : geoSample.getRawDataFiles()) {
      DataFile dataFile = new DataFile(createStringValueElement(rawDataFile),
        Optional.of(new StringValueElement(GEOSoftNames.RAW_FILE_TYPE_NAME)));
      dataFiles.add(dataFile);
    }

    if (geoSample.getCelFile().isPresent()) {
      DataFile dataFile = new DataFile(createStringValueElement(geoSample.getCelFile().get()),
        Optional.of(new StringValueElement(GEOSoftNames.CEL_FILE_TYPE_NAME)));
      dataFiles.add(dataFile);
    }

    if (geoSample.getExpFile().isPresent()) {
      DataFile dataFile = new DataFile(createStringValueElement(geoSample.getExpFile().get()),
        Optional.of(new StringValueElement(GEOSoftNames.EXP_FILE_TYPE_NAME)));
      dataFiles.add(dataFile);
    }

    if (geoSample.getChpFile().isPresent()) {
      DataFile dataFile = new DataFile(createStringValueElement(geoSample.getChpFile().get()),
        Optional.of(new StringValueElement(GEOSoftNames.CHP_FILE_TYPE_NAME)));
      dataFiles.add(dataFile);
    }
    return dataFiles;
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

  private String concatenateFieldValues(List<String> fieldValues)
  {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (String fieldValue : fieldValues) {
      if (!isFirst)
        sb.append(GEOSoftNames.MULTI_VALUE_FIELD_SEPARATOR);
      sb.append(fieldValue);
      isFirst = false;
    }
    return sb.toString();
  }
}
