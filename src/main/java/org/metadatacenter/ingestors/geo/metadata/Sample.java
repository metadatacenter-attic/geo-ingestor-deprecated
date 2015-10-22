package org.metadatacenter.ingestors.geo.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Metadata for a GEO sample.
 *
 * @see GEOMetadata
 */
public class Sample
{
  private final String sampleName;
  private final String title;
  private final String label;
  private final String description;
  private final String platform;
  private final Map<Integer, PerChannelSampleInfo> perChannelInformation;
  private final Optional<String> biomaterialProvider;
  private final List<String> rawDataFiles;
  private final Optional<String> celFile;
  private final Optional<String> expFile;
  private final Optional<String> chpFile;

  public Sample(String sampleName, String title, String label, String description, String platform,
    Map<Integer, PerChannelSampleInfo> perChannelInformation, Optional<String> biomaterialProvider,
    List<String> rawDataFiles, Optional<String> celFile, Optional<String> expFile, Optional<String> chpFile)
  {
    this.sampleName = sampleName;
    this.title = title;
    this.label = label;
    this.description = description;
    this.platform = platform;
    this.perChannelInformation = Collections.unmodifiableMap(perChannelInformation);
    this.biomaterialProvider = biomaterialProvider;
    this.rawDataFiles = Collections.unmodifiableList(rawDataFiles);
    this.celFile = celFile;
    this.expFile = expFile;
    this.chpFile = chpFile;
  }

  public String getSampleName()
  {
    return sampleName;
  }

  public String getTitle()
  {
    return title;
  }

  public String getLabel()
  {
    return label;
  }

  public String getDescription()
  {
    return description;
  }

  public String getPlatform()
  {
    return platform;
  }

  public Map<Integer, PerChannelSampleInfo> getPerChannelInformation()
  {
    return perChannelInformation;
  }

  public Optional<String> getBiomaterialProvider()
  {
    return biomaterialProvider;
  }

  public List<String> getRawDataFiles()
  {
    return rawDataFiles;
  }

  public Optional<String> getCelFile()
  {
    return celFile;
  }

  public Optional<String> getExpFile()
  {
    return expFile;
  }

  public Optional<String> getChpFile()
  {
    return chpFile;
  }

  public Map<String, String> getCharacteristics()
  {
    Map<String, String> characteristics = new HashMap<>();

    for (PerChannelSampleInfo perChannelSampleInfo : this.perChannelInformation.values())
      characteristics.putAll(perChannelSampleInfo.getCharacteristics());

    return characteristics;
  }

  public List<String> getOrganisms()
  {
    List<String> organisms = new ArrayList<>();

    for (PerChannelSampleInfo perChannelSampleInfo : this.perChannelInformation.values())
      organisms.addAll(perChannelSampleInfo.getOrganisms());

    return organisms;
  }

  public List<String> getSourceNames()
  {
    List<String> sourceNames = new ArrayList<>();

    for (PerChannelSampleInfo perChannelSampleInfo : this.perChannelInformation.values())
      sourceNames.add(perChannelSampleInfo.getSourceName());

    return sourceNames;
  }

  public List<String> getMolecules()
  {
    List<String> molecules = new ArrayList<>();

    for (PerChannelSampleInfo perChannelSampleInfo : this.perChannelInformation.values())
      molecules.add(perChannelSampleInfo.getMolecule());

    return molecules;
  }

  public List<String> getLabels()
  {
    List<String> labels = new ArrayList<>();

    for (PerChannelSampleInfo perChannelSampleInfo : this.perChannelInformation.values())
      labels.add(perChannelSampleInfo.getLabel());

    return labels;
  }
}

