package org.metadatacenter.models.geoflat;

import org.metadatacenter.repository.model.DateTemplateFieldInstance;
import org.metadatacenter.repository.model.MetadataTemplateInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GEOFlatTemplateInstance extends MetadataTemplateInstance
{
  public static final List<String> ElementURIs = Collections.singletonList(Namespaces.TEMPLATE_URI_BASE + "GEOFlat");

  private final StringTemplateFieldInstance gse;
  private final StringTemplateFieldInstance seriesTitle;
  private final StringTemplateFieldInstance seriesDescription;
  private final Optional<DateTemplateFieldInstance> submissionDate;
  private final Optional<DateTemplateFieldInstance> publicReleaseDate;
  private final StringTemplateFieldInstance gsm;
  private final StringTemplateFieldInstance platform;
  private final StringTemplateFieldInstance sampleTitle;
  private final StringTemplateFieldInstance sampleLabel;
  private final Optional<StringTemplateFieldInstance> sampleDescription;
  private final Optional<StringTemplateFieldInstance> biomaterialProvider;
  private final Optional<StringTemplateFieldInstance> disease;
  private final Optional<StringTemplateFieldInstance> tissue;
  private final Optional<StringTemplateFieldInstance> sex;
  private final Optional<StringTemplateFieldInstance> age;

  public GEOFlatTemplateInstance(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String templateID,
    StringTemplateFieldInstance gse, StringTemplateFieldInstance seriesTitle,
    StringTemplateFieldInstance seriesDescription, Optional<DateTemplateFieldInstance> submissionDate,
    Optional<DateTemplateFieldInstance> publicReleaseDate, StringTemplateFieldInstance gsm,
    StringTemplateFieldInstance platform, StringTemplateFieldInstance sampleTitle,
    StringTemplateFieldInstance sampleLabel, Optional<StringTemplateFieldInstance> sampleDescription,
    Optional<StringTemplateFieldInstance> biomaterialProvider,
    Optional<StringTemplateFieldInstance> disease,
    Optional<StringTemplateFieldInstance> tissue,
    Optional<StringTemplateFieldInstance> sex,
    Optional<StringTemplateFieldInstance> age)
  {
    super(jsonLDTypes, jsonLDIdentifier, templateID);
    this.gse = gse;
    this.seriesTitle = seriesTitle;
    this.seriesDescription = seriesDescription;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.gsm = gsm;
    this.platform = platform;
    this.sampleTitle = sampleTitle;
    this.sampleLabel = sampleLabel;
    this.sampleDescription = sampleDescription;
    this.biomaterialProvider = biomaterialProvider;
    this.disease = disease;
    this.tissue = tissue;
    this.sex = sex;
    this.age = age;
  }

  public GEOFlatTemplateInstance(String templateID, StringTemplateFieldInstance gse,
    StringTemplateFieldInstance seriesTitle, StringTemplateFieldInstance seriesDescription,
    Optional<DateTemplateFieldInstance> submissionDate, Optional<DateTemplateFieldInstance> publicReleaseDate,
    StringTemplateFieldInstance gsm, StringTemplateFieldInstance platform, StringTemplateFieldInstance sampleTitle,
    StringTemplateFieldInstance sampleLabel, Optional<StringTemplateFieldInstance> sampleDescription,
    Optional<StringTemplateFieldInstance> biomaterialProvider,
    Optional<StringTemplateFieldInstance> disease,
    Optional<StringTemplateFieldInstance> tissue,
    Optional<StringTemplateFieldInstance> sex,
    Optional<StringTemplateFieldInstance> age)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_INSTANCES_URI_BASE), templateID);
    this.gse = gse;
    this.seriesTitle = seriesTitle;
    this.seriesDescription = seriesDescription;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
    this.gsm = gsm;
    this.platform = platform;
    this.sampleTitle = sampleTitle;
    this.sampleLabel = sampleLabel;
    this.sampleDescription = sampleDescription;
    this.biomaterialProvider = biomaterialProvider;
    this.disease = disease;
    this.tissue = tissue;
    this.sex = sex;
    this.age = age;
  }
}
