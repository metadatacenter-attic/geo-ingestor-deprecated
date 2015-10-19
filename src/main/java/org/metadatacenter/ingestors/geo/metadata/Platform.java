package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Metadata for a GEO platform.
 *
 * @see GEOMetadata
 */
public class Platform
{
  private final String title;
  private final String distribution;
  private final String technology;
  private final String organism;
  private final String manufacturer;
  private final List<String> manufacturerProtocol;
  private final List<String> description;
  private final Optional<String> catalogNumber;
  private final Optional<String> webLink;
  private final Optional<String> support;
  private final Optional<String> coating;
  private final List<String> contributor;
  private final List<String> pubmedID;

  public Platform(String title, String distribution, String technology, String organism, String manufacturer,
    List<String> manufacturerProtocol, List<String> description, Optional<String> catalogNumber,
    Optional<String> webLink, Optional<String> support, Optional<String> coating, List<String> contributor,
    List<String> pubmedID)
  {
    this.title = title;
    this.distribution = distribution;
    this.technology = technology;
    this.organism = organism;
    this.manufacturer = manufacturer;
    this.manufacturerProtocol = manufacturerProtocol;
    this.description = description;
    this.catalogNumber = catalogNumber;
    this.webLink = webLink;
    this.support = support;
    this.coating = coating;
    this.contributor = contributor;
    this.pubmedID = pubmedID;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDistribution()
  {
    return distribution;
  }

  public String getTechnology()
  {
    return technology;
  }

  public String getOrganism()
  {
    return organism;
  }

  public String getManufacturer()
  {
    return manufacturer;
  }

  public List<String> getManufacturerProtocol()
  {
    return Collections.unmodifiableList(manufacturerProtocol);
  }

  public List<String> getDescription()
  {
    return description;
  }

  public Optional<String> getCatalogNumber()
  {
    return catalogNumber;
  }

  public Optional<String> getWebLink()
  {
    return webLink;
  }

  public Optional<String> getSupport()
  {
    return support;
  }

  public Optional<String> getCoating()
  {
    return coating;
  }

  public List<String> getContributor()
  {
    return Collections.unmodifiableList(contributor);
  }

  public List<String> getPubmedID()
  {
    return Collections.unmodifiableList(pubmedID);
  }
}
