package org.metadatacenter.repository.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.gsonfire.PostProcessor;

import java.util.List;
import java.util.Optional;

public class MetadataTemplateElementPostProcessor implements PostProcessor<MetadataTemplateElement>
{
  @Override public void postDeserialize(MetadataTemplateElement metadataTemplateElement, JsonElement jsonElement,
      Gson gson)
  {
    if (jsonElement.isJsonObject()) {
      JsonObject jsonObject = jsonElement.getAsJsonObject();
    }
  }

  @Override public void postSerialize(JsonElement jsonElement, MetadataTemplateElement metadataTemplateElement,
      Gson gson)
  {
    if (jsonElement.isJsonObject()) {
      JsonObject obj = jsonElement.getAsJsonObject();

      if (obj.has("jsonLDTypes"))
        obj.remove("jsonLDTypes");

      if (obj.has("jsonLDTypes"))
        obj.remove("jsonLDTypes");

      List<String> jsonLDTypes = metadataTemplateElement.getJSONLDTypes();
      Optional<String> jsonLDIdentifier = metadataTemplateElement.getJSONLDIdentifier();

      if (!jsonLDTypes.isEmpty()) {
        if (jsonLDTypes.size() == 1)
          obj.addProperty("@type", jsonLDTypes.get(0));
        else {
          StringBuilder sb = new StringBuilder("[");
          boolean isFirst = true;
          for (int typeValueIndex = 0; typeValueIndex < jsonLDTypes.size(); typeValueIndex++) {
            if (!isFirst)
              sb.append(", ");
            sb.append(jsonLDTypes.get(typeValueIndex));
            isFirst = false;
          }
          sb.append("]");
          obj.addProperty("@type", sb.toString());
        }
      }

      if (jsonLDIdentifier.isPresent())
        obj.addProperty("@id", jsonLDIdentifier.get());
    }
  }
}
