package de.aboutflash.archerytime.remoteclient.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


/**
 * Class
 *
 * @author falk@aboutflash.de on 11.11.2017.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class StopScreen extends StackPane {

  @SuppressWarnings("WeakerAccess")
  public static final String DEFAULT_STYLE_CLASS = "stop-screen";
  public static final String LABEL_STYLE_CLASS = "stop-screen__label";
  public static final String LABEL_SHADE_STYLE_CLASS = "stop-screen__label-shade";

  public StopScreen() {
    getStyleClass().add(DEFAULT_STYLE_CLASS);

    final String message = "STOP";

    final Label stop = new Label(message);
    stop.getStyleClass().add(LABEL_STYLE_CLASS);

    final Label stopShade = new Label(message);
    stopShade.getStyleClass().add(LABEL_SHADE_STYLE_CLASS);

    final StackPane stopBox = new StackPane(stopShade, stop);
    getChildren().add(stopBox);
    StackPane.setAlignment(stopBox, Pos.CENTER);
  }
}
