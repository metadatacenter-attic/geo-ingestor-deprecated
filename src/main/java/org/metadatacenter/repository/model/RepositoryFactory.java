package org.metadatacenter.repository.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryFactory
{
  public static StringValueElement createStringValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new StringValueElement(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static StringValueElement createStringValueElement(List<String> jsonLDTypes, String value)
  {
    return new StringValueElement(jsonLDTypes, generateJSONLDIdentifier(), value);
  }

  public static StringValueElement createStringValueElement(String value)
  {
    return new StringValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value);
  }

  public static Optional<StringValueElement> createOptionalStringValueElement(Optional<String> value)
  {
    if (value.isPresent())
      return Optional.of(new StringValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value.get()));
    else
      return Optional.empty();
  }

  private static Optional<String> generateJSONLDIdentifier()
  {
    return Optional.of(UUID.randomUUID().toString());
  }

  public static DateValueElement createDateValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier,
    String value)
  {
    return new DateValueElement(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static DateValueElement createDateValueElement(List<String> jsonLDTypes, String value)
  {
    return new DateValueElement(jsonLDTypes, generateJSONLDIdentifier(), value);
  }

  public static DateValueElement createDateValueElement(String value)
  {
    return new DateValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value);
  }

  public static BooleanValueElement createBooleanValueElement(List<String> jsonLDTypes,
    Optional<String> jsonLDIdentifier, Boolean value)
  {
    return new BooleanValueElement(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static BooleanValueElement createBooleanValueElement(List<String> jsonLDTypes, Boolean value)
  {
    return new BooleanValueElement(jsonLDTypes, generateJSONLDIdentifier(), value);
  }

  public static BooleanValueElement createBooleanValueElement(Boolean value)
  {
    return new BooleanValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value);
  }

  public static EmailValueElement createEmailValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String value)
  {
    return new EmailValueElement(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static EmailValueElement createEmailValueElement(List<String> jsonLDTypes, String value)
  {
    return new EmailValueElement(jsonLDTypes, generateJSONLDIdentifier(), value);
  }

  public static EmailValueElement createEmailValueElement(String value)
  {
    return new EmailValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value);
  }

  public static PhoneValueElement createPhoneValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String value)
  {
    return new PhoneValueElement(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static PhoneValueElement createPhoneValueElement(List<String> jsonLDTypes, String value)
  {
    return new PhoneValueElement(jsonLDTypes, generateJSONLDIdentifier(), value);
  }

  public static PhoneValueElement createPhoneValueElement(String value)
  {
    return new PhoneValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value);
  }

  public static URIValueElement createURIValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String value)
  {
    return new URIValueElement(jsonLDTypes, jsonLDIdentifier, value);
  }

  public static URIValueElement createURIValueElement(List<String> jsonLDTypes, String value)
  {
    return new URIValueElement(jsonLDTypes, generateJSONLDIdentifier(), value);
  }

  public static URIValueElement createURIValueElement(String value)
  {
    return new URIValueElement(Collections.emptyList(), generateJSONLDIdentifier(), value);
  }

}
