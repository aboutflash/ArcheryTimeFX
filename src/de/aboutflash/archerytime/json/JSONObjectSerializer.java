package de.aboutflash.archerytime.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.aboutflash.archerytime.model.ScreenState;

import java.io.IOException;


/**
 * Helper class for serialization / deserialization of objects with JSON.
 */
public class JSONObjectSerializer implements ObjectSerializer {

  private ObjectMapper screenStateMapper;

  public JSONObjectSerializer() {
    // create jackson mapper with custom serializers for {@link ScreenState}
    screenStateMapper = new ObjectMapper();

    final SimpleModule screenStateModule = new SimpleModule();
    screenStateModule.addSerializer(new ScreenStateSerializer());
    screenStateModule.addDeserializer(ScreenState.class, new ScreenStateDeserializer(screenStateMapper));
    screenStateMapper.registerModule(screenStateModule);
  }

  /**
   * Serialize a {@link ScreenState} into a JSON string.
   */
  @Override
  public String serializeScreenState(ScreenState screenState) {
    try {
      return screenStateMapper.writeValueAsString(screenState);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deserialize a {@link ScreenState} from a JSON string.
   */
  @Override
  public ScreenState deserializeScreenState(String json) {
    try {
      return screenStateMapper.readValue(json, ScreenState.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
