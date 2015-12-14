package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.EmailValueElement;
import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.PhoneValueElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Contact extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.TEMPLATE_ELEMENT_URI_BASE
    + "Contact");

  private final StringValueElement firstName;
  private final StringValueElement middleInitial;
  private final StringValueElement lastName;
  private final Optional<StringValueElement> address;
  private final Optional<EmailValueElement> email;
  private final Optional<PhoneValueElement> phone;
  private final Optional<PhoneValueElement> fax;
  private final Optional<StringValueElement> role;
  private final Optional<Organization> organization;

  public Contact(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement firstName,
    StringValueElement middleInitial, StringValueElement lastName, Optional<StringValueElement> address,
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

  public Contact(StringValueElement firstName, StringValueElement middleInitial, StringValueElement lastName,
    Optional<StringValueElement> address, Optional<EmailValueElement> email, Optional<PhoneValueElement> phone,
    Optional<PhoneValueElement> fax, Optional<StringValueElement> role, Optional<Organization> organization)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
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

  public Contact(StringValueElement firstName, StringValueElement middleInitial, StringValueElement lastName)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.address = Optional.empty();
    this.email = Optional.empty();;
    this.phone = Optional.empty();;
    this.fax = Optional.empty();;
    this.role = Optional.empty();;
    this.organization = Optional.empty();;
  }
}
