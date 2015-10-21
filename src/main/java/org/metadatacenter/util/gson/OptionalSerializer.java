package org.metadatacenter.util.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalSerializer<T> implements JsonSerializer<Optional<T>>
{
  @Override public JsonElement serialize(Optional<T> src, Type typeOfSrc, JsonSerializationContext context)
  {

    final JsonElement element = context.serialize(src.orElse(null));
    final JsonArray result = new JsonArray();
    result.add(element);
    return result;
  }
}