package de.aboutflash.archerytime.server.ui;

import de.aboutflash.archerytime.server.model.ControlViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Class
 *
 * @author falk@aboutflash.de on 28.11.2017.
 */
public class ControlScreen extends StackPane {
  public static final String DEFAULT_STYLE_CLASS = "control-screen";
  private final ControlViewModel model;
  private final Label status = new Label();

  public ControlScreen(ControlViewModel viewModel) {
    model = viewModel;

    getStyleClass().add(DEFAULT_STYLE_CLASS);
    drawUi();
  }

  private void drawUi() {
    status.textProperty().bind(model.statusProperty());
    getChildren().add(status);
  }

}
