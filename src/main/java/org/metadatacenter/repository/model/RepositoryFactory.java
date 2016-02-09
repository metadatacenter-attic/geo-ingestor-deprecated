package org.metadatacenter.repository.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RepositoryFactory
{
  public static StringTemplateFieldInstance createStringValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new StringTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static StringTemplateFieldInstance createStringValueElement(List<String> jsonLDTypes, String value)
  {
    return new StringTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static StringTemplateFieldInstance createStringValueElement(String value)
  {
    return new StringTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<StringTemplateFieldInstance> createOptionalStringValueElement(Optional<String> value)
  {
    if (value.isPresent())
      return Optional.of(new StringTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value.get()));
    else
      return Optional.empty();
  }

  public static Optional<StringTemplateFieldInstance> createOptionalStringValueElement(String value)
  {
    return Optional.of(new StringTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static DateTemplateFieldInstance createDateValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new DateTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static DateTemplateFieldInstance createDateValueElement(List<String> jsonLDTypes, String value)
  {
    return new DateTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static DateTemplateFieldInstance createDateValueElement(String value)
  {
    return new DateTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<DateTemplateFieldInstance> createOptionalDateValueElement(String value)
  {
    return Optional.of(new DateTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static BooleanTemplateFieldInstance createBooleanValueElement(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, Boolean value)
  {
    return new BooleanTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static BooleanTemplateFieldInstance createBooleanValueElement(List<String> jsonLDTypes, Boolean value)
  {
    return new BooleanTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static BooleanTemplateFieldInstance createBooleanValueElement(Boolean value)
  {
    return new BooleanTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static EmailTemplateFieldInstance createEmailValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new EmailTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static EmailTemplateFieldInstance createEmailValueElement(List<String> jsonLDTypes, String value)
  {
    return new EmailTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static EmailTemplateFieldInstance createEmailValueElement(String value)
  {
    return new EmailTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<EmailTemplateFieldInstance> createOptionalEmailValueElement(String value)
  {
    return Optional.of(new EmailTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static PhoneTemplateFieldInstance createPhoneValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new PhoneTemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static PhoneTemplateFieldInstance createPhoneValueElement(List<String> jsonLDTypes, String value)
  {
    return new PhoneTemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static PhoneTemplateFieldInstance createPhoneValueElement(String value)
  {
    return new PhoneTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

  public static Optional<PhoneTemplateFieldInstance> createOptionalPhoneValueElement(String value)
  {
    return Optional.of(new PhoneTemplateFieldInstance(Collections.emptyList(), Optional.empty(), value));
  }

  public static URITemplateFieldInstance createURIValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new URITemplateFieldInstance(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static URITemplateFieldInstance createURIValueElement(List<String> jsonLDTypes, String value)
  {
    return new URITemplateFieldInstance(jsonLDTypes, Optional.empty(), value);
  }

  public static URITemplateFieldInstance createURIValueElement(String value)
  {
    return new URITemplateFieldInstance(Collections.emptyList(), Optional.empty(), value);
  }

}
