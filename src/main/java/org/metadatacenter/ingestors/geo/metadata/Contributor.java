package org.metadatacenter.ingestors.geo.metadata;

public class Contributor
{
  private final String name;
  private final String email;
  private final String phone;
  private final String fax;
  private final String laboratory;
  private final String department;
  private final String institute;
  private final String address;
  private final String city;
  private final String state;
  private final String zipOrPostalCode;
  private final String country;
  private final String webLink;

  public Contributor(String name, String email, String phone, String fax, String laboratory, String department,
    String institute, String address, String city, String state, String zipOrPostalCode, String country, String webLink)
  {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.fax = fax;
    this.laboratory = laboratory;
    this.department = department;
    this.institute = institute;
    this.address = address;
    this.city = city;
    this.state = state;
    this.zipOrPostalCode = zipOrPostalCode;
    this.country = country;
    this.webLink = webLink;
  }
}
