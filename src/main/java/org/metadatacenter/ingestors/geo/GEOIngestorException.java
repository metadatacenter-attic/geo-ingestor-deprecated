package org.metadatacenter.ingestors.geo;

public class GEOIngestorException extends Exception
{
  public GEOIngestorException(String message)
  {
    super(message);
  }

  public GEOIngestorException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
