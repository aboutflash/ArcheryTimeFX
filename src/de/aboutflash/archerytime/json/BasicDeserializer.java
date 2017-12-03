package de.aboutflash.archerytime.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull;


/**
 * Basic helper class for serialization.
 */
abstract class BasicDeserializer<T> extends StdDeserializer<T> {

  private final ObjectMapper mapper;

  /**
   * Constructor.
   */
  public BasicDeserializer(Class<T> vc, ObjectMapper mapper) {
    super(vc);
    this.mapper = mapper;
  }

  @Override
  public final T deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
    final JsonNode node = parser.getCodec().readTree(parser);
    return deserialize(node);
  }

  protected abstract T deserialize(JsonNode node) throws IOException;

  /**
   * Easier deserialization of nested objects. If the node does not contain an element with the
   * given name, this methods returns null.
   */
  protected <T> T read(JsonNode node, String name, Class<T> clazz) throws IOException {
    checkNotNull(name, "No name given");

    if (!node.has(name)) {
      return null;
    }

    return read(node.get(name), clazz);
  }

  /**
   * Parses the node into an object of the given class argument. This uses the configured default
   * de-serializer for the class.
   */
  protected <T> T read(JsonNode node, Class<T> clazz) throws IOException {
    checkNotNull(node, "No node given");
    checkNotNull(clazz, "No class given");

    return mapper.readValue(node.traverse(mapper), clazz);
  }

  /**
   * Parses a key of the node as boolean or returns the defaultValue if the key isn't present.
   */
  protected boolean readBoolean(JsonNode node, String name, boolean defaultValue) {
    if (!node.has(name)) {
      return defaultValue;
    }
    return node.get(name).asBoolean();
  }

}
