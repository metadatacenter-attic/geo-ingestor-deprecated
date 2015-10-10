package org.metadatacenter.converters.geo.metadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Metadata for a GEO series. See http://www.ncbi.nlm.nih.gov/geo/info/spreadsheet.html#GAmeta.
 */
public class Series
{
  private final String title;
  private final String summary;
  private final String overallDesign;
  private final List<String> contributors;
  private final List<String> pubmedIDs;
  private final Map<String, Map<String, String>> variables; // sample name -> (variable -> value)
  private final Map<String, List<String>> repeat; // sample name -> [repeat type]

  public Series(String title, String summary, String overallDesign, List<String> contributors, List<String> pubmedIDs,
    Map<String, Map<String, String>> variables, Map<String, List<String>> repeat)
  {
    this.title = title;
    this.summary = summary;
    this.overallDesign = overallDesign;
    this.contributors = contributors;
    this.pubmedIDs = pubmedIDs;
    this.variables = variables;
    this.repeat = repeat;
  }

  public String getTitle()
  {
    return title;
  }

  public String getSummary()
  {
    return summary;
  }

  public String getOverallDesign()
  {
    return overallDesign;
  }

  public List<String> getContributors()
  {
    return Collections.unmodifiableList(contributors);
  }

  public List<String> getPubmedIDs()
  {
    return Collections.unmodifiableList(pubmedIDs);
  }

  public Map<String, Map<String, String>> getVariables()
  {
    return Collections.unmodifiableMap(variables);
  }

  public Map<String, List<String>> getRepeat()
  {
    return Collections.unmodifiableMap(repeat);
  }
}
