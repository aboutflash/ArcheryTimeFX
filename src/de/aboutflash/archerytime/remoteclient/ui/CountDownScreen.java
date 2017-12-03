package de.aboutflash.archerytime.remoteclient.ui;

import de.aboutflash.archerytime.remoteclient.model.CountdownViewModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Class
 *
 * @author falk@aboutflash.de on 23.11.2017.
 */
public class CountDownScreen extends StackPane {
  @SuppressWarnings("WeakerAccess")
  public static final String DEFAULT_STYLE_CLASS = "countdown-screen";
  @SuppressWarnings("WeakerAccess")
  public static final String COUNTER_STYLE_CLASS = "countdown-screen__counter";
  @SuppressWarnings("WeakerAccess")
  public static final String COUNTER_SHADE_STYLE_CLASS = "countdown-screen__counter-shade";

  private final CountdownViewModel model;

  public CountDownScreen(CountdownViewModel viewModel) {
    model = viewModel;

    getStyleClass().add(DEFAULT_STYLE_CLASS);
    drawUi();
  }

  private void drawUi() {
    final HBox hBox = new HBox(createSequenceDisplay(), createCounter());
    hBox.setFillHeight(true);
    getChildren().add(hBox);
    StackPane.setAlignment(hBox, Pos.CENTER);
  }

  private Node createSequenceDisplay() {
    final SequenceDisplay sequenceDisplay = new SequenceDisplay();
    sequenceDisplay.sequenceProperty().bind(model.getSequenceProperty());
    HBox.setHgrow(sequenceDisplay, Priority.NEVER);
    return sequenceDisplay;
  }

  private Node createCounter() {
    final Label counter = new Label();
    counter.getStyleClass().add(COUNTER_STYLE_CLASS);
    counter.textProperty().bind(model.countdownProperty().asString());

    final Label counterShade = new Label();
    counterShade.getStyleClass().add(COUNTER_SHADE_STYLE_CLASS);
    counterShade.textProperty().bind(model.countdownProperty().asString());

    final StackPane counterBox = new StackPane(counterShade, counter);
    StackPane.setAlignment(counter, Pos.CENTER_RIGHT);
    StackPane.setAlignment(counterShade, Pos.CENTER_RIGHT);

    HBox.setHgrow(counterBox, Priority.SOMETIMES);
    return counterBox;
  }
}
