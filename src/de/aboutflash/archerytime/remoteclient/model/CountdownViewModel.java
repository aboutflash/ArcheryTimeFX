package de.aboutflash.archerytime.remoteclient.model;

import de.aboutflash.archerytime.model.ScreenState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Class
 *
 * @author falk@aboutflash.de on 23.11.2017.
 */
public class CountdownViewModel {
  public int getCountdown() {
    return countdown.get();
  }

  public IntegerProperty countdownProperty() {
    return countdown;
  }

  public void setCountdown(final int value) {
    countdown.set(value);
  }

  private IntegerProperty countdown = new SimpleIntegerProperty();


  public ObjectProperty<ScreenState.Sequence> getSequenceProperty() {
    return sequence;
  }

  public void setSequence(final ScreenState.Sequence value) {
    sequence.set(value);
  }

  public ScreenState.Sequence getSequence() {
    return sequence.get();
  }

  private ObjectProperty<ScreenState.Sequence> sequence = new SimpleObjectProperty<>();
}
