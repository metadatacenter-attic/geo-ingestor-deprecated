package org.metadatacenter.repository.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RepositoryFactory
{
  public static StringTemplateFieldInstance createStringTemplateFieldInstance(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, String value)
  {
    return new StringTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static StringTemplateFieldInstance createStringTemplateFieldInstance(List<String> jsonLDTypes, String value)
  {
    return new StringTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static StringTemplateFieldInstance createStringTemplateFieldInstance(String value)
  {
    return new StringTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<StringTemplateFieldInstance> createOptionalStringTemplateFieldInstance(Optional<String> value)
  {
    if (value.isPresent())
      return Optional.of(new StringTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value.get()));
    else
      return Optional.empty();
  }

  public static Optional<StringTemplateFieldInstance> createOptionalStringTemplateFieldInstance(String value)
  {
    return Optional.of(new StringTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static DateTemplateFieldInstance createDateTemplateFieldInstance(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, String value)
  {
    return new DateTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static DateTemplateFieldInstance createDateTemplateFieldInstance(List<String> jsonLDTypes, String value)
  {
    return new DateTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static DateTemplateFieldInstance createDateTemplateFieldInstance(String value)
  {
    return new DateTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<DateTemplateFieldInstance> createOptionalDateTemplateFieldInstance(String value)
  {
    return Optional.of(new DateTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static BooleanTemplateFieldInstance createBooleanTemplateFieldInstance(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, Boolean value)
  {
    return new BooleanTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static BooleanTemplateFieldInstance createBooleanTemplateFieldInstance(List<String> jsonLDTypes, Boolean value)
  {
    return new BooleanTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static BooleanTemplateFieldInstance createBooleanTemplateFieldInstance(Boolean value)
  {
    return new BooleanTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static EmailTemplateFieldInstance createEmailTemplateFieldInstance(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, String value)
  {
    return new EmailTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static EmailTemplateFieldInstance createEmailTemplateFieldInstance(List<String> jsonLDTypes, String value)
  {
    return new EmailTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static EmailTemplateFieldInstance createEmailTemplateFieldInstance(String value)
  {
    return new EmailTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<EmailTemplateFieldInstance> createOptionalEmailTemplateFieldInstance(String value)
  {
    return Optional.of(new EmailTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static PhoneTemplateFieldInstance createPhoneTemplateFieldInstance(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, String value)
  {
    return new PhoneTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static PhoneTemplateFieldInstance createPhoneTemplateFieldInstance(List<String> jsonLDTypes, String value)
  {
    return new PhoneTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static PhoneTemplateFieldInstance createPhoneTemplateFieldInstance(String value)
  {
    return new PhoneTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<PhoneTemplateFieldInstance> createOptionalPhoneTemplateFieldInstance(String value)
  {
    return Optional.of(new PhoneTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static URITemplateFieldInstance createURITemplateFieldInstance(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, String value)
  {
    return new URITemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static URITemplateFieldInstance createURITemplateFieldInstance(List<String> jsonLDTypes, String value)
  {
    return new URITemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static URITemplateFieldInstance createURITemplateFieldInstance(String value)
  {
    return new URITemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

}
