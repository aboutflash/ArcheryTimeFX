package de.aboutflash.archerytime.json;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.aboutflash.archerytime.model.ScreenState;

import java.io.IOException;

import static de.aboutflash.archerytime.model.ScreenState.*;
import static de.aboutflash.archerytime.model.ScreenState.Screen.MESSAGE;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.NONE;


/**
 * A JSON/Jackson Deserializer for {@link ScreenState}.
 */
public class ScreenStateDeserializer extends BasicDeserializer<ScreenState> {

  /**
   * Constructor.
   */
  public ScreenStateDeserializer(ObjectMapper mapper) {
    super(ScreenState.class, mapper);
  }

  @Override
  protected ScreenState deserialize(final JsonNode node) throws IOException {
    final Screen screen = node.has(SCREEN_FIELD) ? Screen.valueOf(node.get(SCREEN_FIELD).asText()) : MESSAGE;
    final Sequence sequence = node.has(SEQUENCE_FIELD) ? Sequence.valueOf(node.get(SEQUENCE_FIELD).asText()) : NONE;
    final Integer time = node.has(TIME_FIELD) ? node.get(TIME_FIELD).asInt() : null;
    final String message = node.has(MESSAGE_FIELD) ? node.get(MESSAGE_FIELD).asText() : "";

    // it's a cont down - ignoring any message
    if (screen != MESSAGE && sequence != NONE)
      return new ScreenState(screen, sequence, time);

    // it's a plain message - ignoring other fields
    if (screen == MESSAGE)
      return new ScreenState(screen, message);

    return DEFAULT;
  }

}
