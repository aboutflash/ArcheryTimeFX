package de.aboutflash.archerytime.model;

import java.io.Serializable;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
@SuppressWarnings("ParameterHidesMemberVariable")
public class ScreenState implements Serializable {

  private static final long serialVersionUID = -5614470914224363886L;

  public static final ScreenState DEFAULT = new ScreenState(Screen.INVALID);

  public static final String SCREEN_FIELD = "scr";
  public static final String SEQUENCE_FIELD = "seq";
  public static final String TIME_FIELD = "time";
  public static final String MESSAGE_FIELD = "msg";

  private final Screen screen;
  private final Sequence sequence;
  private final Integer seconds;
  private final String message;

  public ScreenState(final Screen screen, final Sequence sequence, final Integer seconds) {
    this.screen = screen;
    this.sequence = sequence;
    this.seconds = seconds;
    this.message = "";
  }

  public ScreenState(final Screen screen, final String message) {
    this.screen = screen;
    this.message = message;
    this.sequence = Sequence.NONE;
    this.seconds = null;
  }

  public ScreenState(final Screen screen) {
    this.screen = screen;
    this.message = "";
    this.sequence = Sequence.NONE;
    this.seconds = null;
  }

  public Screen getScreen() {
    return screen;
  }

  public Sequence getSequence() {
    return sequence;
  }

  public Integer getSeconds() {
    return seconds;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "ScreenState{" +
        "screen=" + screen +
        ", sequence=" + sequence +
        ", seconds=" + seconds +
        ", message='" + message + '\'' +
        '}';
  }

  @SuppressWarnings("PublicInnerClass")
  public enum Screen {
    INVALID,
    MESSAGE,
    STOP,
    STEADY,
    SHOOT,
    SHOOT_UP30,
  }

  @SuppressWarnings("PublicInnerClass")
  public enum Sequence {
    NONE,
    A,
    B,
    AB,
    CD,
  }
}
