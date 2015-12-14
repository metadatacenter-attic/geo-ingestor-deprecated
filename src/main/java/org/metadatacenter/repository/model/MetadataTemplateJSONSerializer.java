package org.metadatacenter.repository.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gsonfire.GsonFireBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * GSON- and GSON Fire-based JSON serializer for metadata templates.
 *
 * @see MetadataTemplateElement
 * @see MetadataTemplateElementPostProcessor
 * @see OptionalTypeAdapter
 */
public class MetadataTemplateJSONSerializer<T extends MetadataTemplate>
{
  public final static String JSON_FILE_ENCODING = "UTF-8";

  public void serialize(T metadataTemplate, String jsonFileName) throws IOException
  {
    System.out.println("Generating JSON file " + jsonFileName);

    try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFileName), JSON_FILE_ENCODING)) {
      GsonFireBuilder fireBuilder = new GsonFireBuilder();
      fireBuilder.enableExclusionByValue();

      fireBuilder.registerPostProcessor(MetadataTemplateElement.class, new MetadataTemplateElementPostProcessor());

      GsonBuilder gsonBuilder = fireBuilder.createGsonBuilder();

      gsonBuilder.registerTypeAdapterFactory(OptionalTypeAdapter.FACTORY);

      gsonBuilder.setPrettyPrinting();
      Gson gson = gsonBuilder.create();
      gson.toJson(metadataTemplate, writer);
    }
  }
}
