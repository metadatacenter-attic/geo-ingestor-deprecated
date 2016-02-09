package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.EmailTemplateFieldInstance;
import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.PhoneTemplateFieldInstance;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Contact extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList(Namespaces.TEMPLATE_ELEMENT_URI_BASE + "Contact");

  private final StringTemplateFieldInstance firstName;
  private final StringTemplateFieldInstance middleInitial;
  private final StringTemplateFieldInstance lastName;
  private final Optional<StringTemplateFieldInstance> address;
  private final Optional<EmailTemplateFieldInstance> email;
  private final Optional<PhoneTemplateFieldInstance> phone;
  private final Optional<PhoneTemplateFieldInstance> fax;
  private final Optional<StringTemplateFieldInstance> role;
  private final Optional<Organization> organization;

  public Contact(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance firstName,
    StringTemplateFieldInstance middleInitial, StringTemplateFieldInstance lastName,
    Optional<StringTemplateFieldInstance> address, Optional<EmailTemplateFieldInstance> email,
    Optional<PhoneTemplateFieldInstance> phone, Optional<PhoneTemplateFieldInstance> fax,
    Optional<StringTemplateFieldInstance> role, Optional<Organization> organization)
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

  public Contact(StringTemplateFieldInstance firstName, StringTemplateFieldInstance middleInitial,
    StringTemplateFieldInstance lastName, Optional<StringTemplateFieldInstance> address,
    Optional<EmailTemplateFieldInstance> email, Optional<PhoneTemplateFieldInstance> phone,
    Optional<PhoneTemplateFieldInstance> fax, Optional<StringTemplateFieldInstance> role,
    Optional<Organization> organization)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
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

  public Contact(StringTemplateFieldInstance firstName, StringTemplateFieldInstance middleInitial,
    StringTemplateFieldInstance lastName)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.address = Optional.empty();
    this.email = Optional.empty();
    ;
    this.phone = Optional.empty();
    ;
    this.fax = Optional.empty();
    ;
    this.role = Optional.empty();
    ;
    this.organization = Optional.empty();
    ;
  }
}
