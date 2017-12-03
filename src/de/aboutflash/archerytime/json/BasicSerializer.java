package de.aboutflash.archerytime.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import static com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull;


public abstract class BasicSerializer<T> extends StdSerializer<T> {

  public BasicSerializer(Class<T> t) {
    super(t);
  }

  /**
   * Serializes a LocalDateTime in the used ISO format yyyyMMddHHss.
   */
  protected void writeDateTime(
      JsonGenerator generator, String name)
      throws IOException {
    checkNotNull(generator, "No generator given");
    checkNotNull(name, "No name given");
  }
}
