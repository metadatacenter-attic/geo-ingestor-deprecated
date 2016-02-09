package org.metadatacenter.repository.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.gsonfire.PostProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * GSON Fire directives in the {@link MetadataTemplateElementInstance} class  exclude the
 * {@link MetadataTemplateElementInstance#jsonLDTypes} and {@link MetadataTemplateElementInstance#jsonLDIdentifier} fields from direct
 * serialization. Here we serialize them as JSON-LD-conforming <tt>@type</tt> and <tt>@id</tt> fields.
 * </p>
 * Note that the corresponding deserialization logic has not been implemented.
 *
 * @see MetadataTemplateElementInstance
 * @see MetadataTemplateInstanceJSONSerializer
 * @see JSONLDContext
 */
public class MetadataTemplateElementPostProcessor implements PostProcessor<MetadataTemplateElementInstance>
{
  @Override public void postDeserialize(MetadataTemplateElementInstance metadataTemplateElementInstance, JsonElement jsonElement,
    Gson gson)
  {
  }

  @Override public void postSerialize(JsonElement jsonElement, MetadataTemplateElementInstance metadataTemplateElementInstance,
    Gson gson)
  {
    if (jsonElement.isJsonObject()) {
      JsonObject obj = jsonElement.getAsJsonObject();

      // Remove empty arrays
      Set<String> keysToRemove = new HashSet<>();
      for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
        if (entry.getValue().isJsonArray()) {
          JsonArray arr = entry.getValue().getAsJsonArray();
          if (arr.size() == 0)
            keysToRemove.add(entry.getKey());
        }
      }
      for (String key : keysToRemove)
        obj.remove(key);

      if (obj.has("jsonLDContext"))
        obj.remove("jsonLDContext");

      if (obj.has("jsonLDTypes"))
        obj.remove("jsonLDTypes");

      if (obj.has("jsonLDIdentifier"))
        obj.remove("jsonLDIdentifier");

      Optional<JSONLDContext> jsonLDContext = metadataTemplateElementInstance.getJSONLDContext();
      List<String> jsonLDTypes = metadataTemplateElementInstance.getJSONLDTypes();
      Optional<String> jsonLDIdentifier = metadataTemplateElementInstance.getJSONLDIdentifier();

      if (jsonLDContext.isPresent()) {
        List<JSONLDContextEntry> jsonLDContextEntries = jsonLDContext.get().getJSONLDContextEntries();
        JsonObject contextObject = new JsonObject();
        for (int contextValueIndex = 0; contextValueIndex < jsonLDContextEntries.size(); contextValueIndex++) {
          JSONLDContextEntry jsonLDContextEntry = jsonLDContextEntries.get(contextValueIndex);
          contextObject.addProperty(jsonLDContextEntry.getPropertyName(), jsonLDContextEntry.getPropertyURI());
        }
        obj.add("@context", contextObject);
      }

      if (jsonLDIdentifier.isPresent())
        obj.addProperty("@id", jsonLDIdentifier.get());

      if (!jsonLDTypes.isEmpty()) {
        if (jsonLDTypes.size() == 1)
          obj.addProperty("@type", jsonLDTypes.get(0));
        else {
          JsonArray jsonLDTypesArray = new JsonArray();
          for (int typeValueIndex = 0; typeValueIndex < jsonLDTypes.size(); typeValueIndex++) {
            JsonElement jsonLDType = new JsonPrimitive(jsonLDTypes.get(typeValueIndex));
            jsonLDTypesArray.add(jsonLDType);
          }
          obj.add("@type", jsonLDTypesArray);
        }
      }
    }
  }
}
