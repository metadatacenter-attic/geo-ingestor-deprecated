package org.metadatacenter.model.investigation;

import org.metadatacenter.model.repository.EmailValueElement;
import org.metadatacenter.model.repository.MetadataTemplateElement;
import org.metadatacenter.model.repository.PhoneValueElement;
import org.metadatacenter.model.repository.StringValueElement;

import java.util.List;
import java.util.Optional;

public class Contact extends MetadataTemplateElement
{
  private final StringValueElement firstName;
  private final Optional<StringValueElement> middleInitial;
  private final StringValueElement lastName;
  private final Optional<StringValueElement> address;
  private final Optional<EmailValueElement> email;
  private final Optional<PhoneValueElement> phone;
  private final Optional<PhoneValueElement> fax;
  private final Optional<StringValueElement> role;
  private final Optional<Organization> organization;

  public Contact(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement firstName,
    Optional<StringValueElement> middleInitial, StringValueElement lastName, Optional<StringValueElement> address,
    Optional<EmailValueElement> email, Optional<PhoneValueElement> phone, Optional<PhoneValueElement> fax,
    Optional<StringValueElement> role, Optional<Organization> organization)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.fax = fax;
    this.role = role;
    this.organization = organization;
  }
}
