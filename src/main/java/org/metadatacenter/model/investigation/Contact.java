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
  private final StringValueElement middleInitial;
  private final StringValueElement lastName;
  private final StringValueElement address;
  private final EmailValueElement email;
  private final PhoneValueElement phone;
  private final PhoneValueElement fax;
  private final StringValueElement role;

  public Contact(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement firstName,
      StringValueElement middleInitial, StringValueElement lastName, StringValueElement address,
      EmailValueElement email, PhoneValueElement phone, PhoneValueElement fax, StringValueElement role)
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
  }
}
