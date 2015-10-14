package org.metadatacenter.repository.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class MetadataTemplateElementSerializer implements JsonSerializer<MetadataTemplateElement>
{
  @Override public JsonElement serialize(MetadataTemplateElement src, Type typeOfSrc, JsonSerializationContext context)
  {
    List<String> jsonLDTypes = src.getJSONLDTypes();
    Optional<String> jsonLDID = src.jsonLDIdentifier;

    JsonObject obj = new JsonObject();

    obj.addProperty("$schema", "http://json-schema.org/draft-04/schema#");

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

    if (jsonLDID.isPresent())
      obj.addProperty("@id", jsonLDID.get());

    return obj;
  }
}
