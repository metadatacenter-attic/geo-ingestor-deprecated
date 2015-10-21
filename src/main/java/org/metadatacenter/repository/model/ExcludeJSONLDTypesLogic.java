package org.metadatacenter.repository.model;

import io.gsonfire.gson.ExclusionByValueStrategy;

import java.util.List;

public class ExcludeJSONLDTypesLogic implements ExclusionByValueStrategy<List<String>>
{
  @Override
  public boolean shouldSkipField(List<String> jsonLDTypes) {

    return jsonLDTypes.isEmpty();
  }
}
