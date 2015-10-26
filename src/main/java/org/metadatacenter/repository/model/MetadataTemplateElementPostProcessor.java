package org.metadatacenter.repository.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.gsonfire.PostProcessor;

import java.util.List;
import java.util.Optional;

/**
 * GSON Fire directives in the {@link MetadataTemplateElement} class  exclude the
 * {@link MetadataTemplateElement#jsonLDTypes} and {@link MetadataTemplateElement#jsonLDIdentifier} fields from direct
 * serialization. Here we serialize them as JSON-LD-conforming <tt>@type</tt> and <tt>@id</tt> fields.
 * </p>
 * Note that the corresponding deserialization logic has not been implemented.
 *
 * @see MetadataTemplateElement
 * @see MetadataTemplateJSONSerializer
 */
public class MetadataTemplateElementPostProcessor implements PostProcessor<MetadataTemplateElement>
{
  @Override public void postDeserialize(MetadataTemplateElement metadataTemplateElement, JsonElement jsonElement,
      Gson gson)
  {
  }

  @Override public void postSerialize(JsonElement jsonElement, MetadataTemplateElement metadataTemplateElement,
      Gson gson)
  {
    if (jsonElement.isJsonObject()) {
      JsonObject obj = jsonElement.getAsJsonObject();

      if (obj.has("jsonLDTypes"))
        obj.remove("jsonLDTypes");

      if (obj.has("jsonLDIdentifier"))
        obj.remove("jsonLDIdentifier");

      List<String> jsonLDTypes = metadataTemplateElement.getJSONLDTypes();
      Optional<String> jsonLDIdentifier = metadataTemplateElement.getJSONLDIdentifier();

      if (jsonLDIdentifier.isPresent())
        obj.addProperty("@id", jsonLDIdentifier.get());

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
    }
  }
}
