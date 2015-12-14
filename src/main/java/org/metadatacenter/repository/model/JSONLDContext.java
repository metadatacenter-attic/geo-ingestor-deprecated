package org.metadatacenter.repository.model;

import java.util.Collections;
import java.util.List;

public class JSONLDContext
{
  private final List<JSONLDContextEntry> jsonLDContextEntries;

  public JSONLDContext()
  {
    this.jsonLDContextEntries = Collections.emptyList();
  }

  public JSONLDContext(List<JSONLDContextEntry> jsonLDContextEntries)
  {
    this.jsonLDContextEntries = jsonLDContextEntries;
  }

  public JSONLDContext(JSONLDContextEntry jsonLDContextEntry)
  {
    this.jsonLDContextEntries = Collections.singletonList(jsonLDContextEntry);
  }

  public boolean hasNoContextEntries() { return this.jsonLDContextEntries.isEmpty(); }

  public List<JSONLDContextEntry> getJSONLDContextEntries()
  {
    return this.jsonLDContextEntries;
  }
}
