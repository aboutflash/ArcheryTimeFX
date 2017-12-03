package de.aboutflash.archerytime.remoteclient.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class
 *
 * @author falk@aboutflash.de on 19.11.2017.
 */
public class StartupViewModel {
  private StringProperty message = new SimpleStringProperty("waiting for server");

  public String getMessage() {
    return message.get();
  }

  public StringProperty messageProperty() {
    return message;
  }

  public void setMessage(final String message) {
    this.message.set(message);
  }
}
