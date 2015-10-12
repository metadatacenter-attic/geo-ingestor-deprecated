package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Metadata for a GEO sample. See http://www.ncbi.nlm.nih.gov/geo/info/spreadsheet.html#GAmeta.
 */
public class Sample
{
  private final String sampleName;
  private final String title;
  private final List<String> rawDataFiles;
  private final Optional<String> celFile;
  private final Optional<String> expFile;
  private final Optional<String> chpFile;
  private final String sourceName;
  private final List<String> organisms;
  private final Map<String, String> characteristics; // characteristic -> value
  private final Optional<String> biomaterialProvider;
  private final String molecule;
  private final String label;
  private final String description;
  private final String platform;

  public Sample(String sampleName, String title, List<String> rawDataFiles, Optional<String> celFile,
    Optional<String> expFile, Optional<String> chpFile, String sourceName, List<String> organisms,
    Map<String, String> characteristics, Optional<String> biomaterialProvider, String molecule, String label,
    String description, String platform)
  {
    this.sampleName = sampleName;
    this.title = title;
    this.rawDataFiles = rawDataFiles;
    this.celFile = celFile;
    this.expFile = expFile;
    this.chpFile = chpFile;
    this.sourceName = sourceName;
    this.organisms = organisms;
    this.characteristics = characteristics;
    this.biomaterialProvider = biomaterialProvider;
    this.molecule = molecule;
    this.label = label;
    this.description = description;
    this.platform = platform;
  }

  public String getSampleName()
  {
    return sampleName;
  }

  public String getTitle()
  {
    return title;
  }

  public List<String> getRawDataFiles()
  {
    return rawDataFiles;
  }

  public Optional<String> getCELFile()
  {
    return celFile;
  }

  public Optional<String> getEXPFile()
  {
    return expFile;
  }

  public Optional<String> getCHPFile()
  {
    return chpFile;
  }

  public String getSourceName()
  {
    return sourceName;
  }

  public List<String> getOrganisms()
  {
    return Collections.unmodifiableList(organisms);
  }

  public Map<String, String> getCharacteristics()
  {
    return Collections.unmodifiableMap(characteristics);
  }

  public Optional<String> getBiomaterialProvider()
  {
    return biomaterialProvider;
  }

  public String getMolecule()
  {
    return molecule;
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
}
