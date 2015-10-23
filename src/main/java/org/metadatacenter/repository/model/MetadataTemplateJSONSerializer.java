package org.metadatacenter.repository.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gsonfire.GsonFireBuilder;
import org.metadatacenter.util.gson.OptionalTypeAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MetadataTemplateJSONSerializer<T extends MetadataTemplate>
{
  private final String jsonFileName;

  public final static String JSON_FILE_ENCODING = "UTF-8";

  public MetadataTemplateJSONSerializer(String jsonFileName)
  {
    this.jsonFileName = jsonFileName;
  }

  public void serialize(T metadataTemplate) throws IOException
  {
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
