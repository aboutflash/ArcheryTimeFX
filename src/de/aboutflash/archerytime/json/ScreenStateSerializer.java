package de.aboutflash.archerytime.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.aboutflash.archerytime.model.ScreenState;

import java.io.IOException;

import static com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull;
import static de.aboutflash.archerytime.model.ScreenState.*;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.NONE;


/**
 * A JSON/Jackson Serializer for {@link ScreenState}.
 */
public class ScreenStateSerializer extends StdSerializer<ScreenState> {

  /**
   * Constructor.
   */
  public ScreenStateSerializer() {
    super(ScreenState.class);
  }

  @Override
  public void serialize(final ScreenState value, final JsonGenerator gen, final SerializerProvider provider)
      throws IOException {
    checkNotNull(value, "No value given");

    gen.writeStartObject();
    {
      gen.writeObjectField(SCREEN_FIELD, value.getScreen());

      if (hasSequence(value))
        gen.writeObjectField(SEQUENCE_FIELD, value.getSequence());

      if (hasTime(value))
        gen.writeObjectField(TIME_FIELD, value.getSeconds());

      if (hasMessage(value))
        gen.writeObjectField(MESSAGE_FIELD, value.getMessage());
    }
    gen.writeEndObject();
  }

  private boolean hasSequence(ScreenState value) {
    return value.getSequence() != null && value.getSequence() != NONE;
  }

  private boolean hasTime(ScreenState value) {
    return value.getSeconds() != null;
  }

  private boolean hasMessage(ScreenState value) {
    return value.getMessage() != null && !value.getMessage().isEmpty();
  }
}
