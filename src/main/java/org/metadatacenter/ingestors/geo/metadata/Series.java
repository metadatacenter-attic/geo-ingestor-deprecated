package org.metadatacenter.ingestors.geo.metadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Metadata for a GEO series.
 *
 * @see GEOMetadata
 */
public class Series
{
  private final String title;
  private final List<String> summary;
  private final List<String> overallDesign;
  private final List<Contributor> contributors;
  private final List<String> pubmedIDs;
  private final Map<String, Map<String, String>> variables; // sample ID -> (variable name -> value)
  private final Map<String, List<String>> repeats; // sample ID -> [repeats type]

  public Series(String title, List<String> summary, List<String> overallDesign, List<Contributor> contributors,
    List<String> pubmedIDs, Map<String, Map<String, String>> variables, Map<String, List<String>> repeats)
  {
    this.title = title;
    this.summary = Collections.unmodifiableList(summary);
    this.overallDesign = Collections.unmodifiableList(overallDesign);
    this.contributors = Collections.unmodifiableList(contributors);
    this.pubmedIDs = Collections.unmodifiableList(pubmedIDs);
    this.variables = Collections.unmodifiableMap(variables);
    this.repeats = Collections.unmodifiableMap(repeats);
  }

  public String getTitle()
  {
    return title;
  }

  public List<String> getSummary()
  {
    return summary;
  }

  public List<String> getOverallDesign()
  {
    return overallDesign;
  }

  public List<Contributor> getContributors()
  {
    return contributors;
  }

  public List<String> getPubmedIDs()
  {
    return pubmedIDs;
  }

  public Map<String, Map<String, String>> getVariables()
  {
    return variables;
  }

  public Map<String, List<String>> getRepeats()
  {
    return repeats;
  }

  @Override public String toString()
  {
    return "Series{" +
      "\n title='" + title + '\'' +
      "\n summary='" + summary + '\'' +
      "\n overallDesign='" + overallDesign + '\'' +
      "\n contributors=" + contributors +
      "\n pubmedIDs=" + pubmedIDs +
      "\n variables=" + variables +
      "\n repeats=" + repeats +
      '}';
  }
}
