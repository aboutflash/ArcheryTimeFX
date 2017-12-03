package de.aboutflash.archerytime.server.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class
 *
 * @author falk@aboutflash.de on 28.11.2017.
 */
public class ControlViewModel {

  public ControlViewModel() {
  }

  private final StringProperty status = new SimpleStringProperty();

  public String getStatus() {
    return status.get();
  }

  public void setStatus(final String value) {
    status.set(value);
  }

  public StringProperty statusProperty() {
    return status;
  }
}
