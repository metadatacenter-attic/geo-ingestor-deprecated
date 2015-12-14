package org.metadatacenter.repository.model;

import io.gsonfire.gson.ExclusionByValueStrategy;

import java.util.Optional;

public class ExcludeJSONLDContextLogic implements ExclusionByValueStrategy<Optional<JSONLDContext>>
{
  @Override public boolean shouldSkipField(Optional<JSONLDContext> jsonLDContext)
  {
    return !jsonLDContext.isPresent() || jsonLDContext.get().hasNoContextEntries();
  }
}