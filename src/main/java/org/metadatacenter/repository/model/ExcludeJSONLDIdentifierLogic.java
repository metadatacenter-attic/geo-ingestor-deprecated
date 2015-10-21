package org.metadatacenter.repository.model;

import io.gsonfire.gson.ExclusionByValueStrategy;

import java.util.Optional;

public class ExcludeJSONLDIdentifierLogic implements ExclusionByValueStrategy<Optional<String>>
{
  @Override public boolean shouldSkipField(Optional<String> jsonLDIdentifier)
  {
    return !jsonLDIdentifier.isPresent();
  }
}
