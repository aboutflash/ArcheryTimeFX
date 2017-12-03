package de.aboutflash.archerytime.remoteclient.app;

import de.aboutflash.archerytime.model.ScreenState;
import de.aboutflash.archerytime.remoteclient.model.CountdownViewModel;
import de.aboutflash.archerytime.remoteclient.model.StartupViewModel;
import de.aboutflash.archerytime.remoteclient.net.Listener;
import de.aboutflash.archerytime.remoteclient.ui.CountDownScreen;
import de.aboutflash.archerytime.remoteclient.ui.StopScreen;
import de.aboutflash.archerytime.remoteclient.ui.StartupScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * The dumb display application.
 * It simply shows, what the control server says.
 *
 * @author falk@aboutflash.de on 22.11.2017.
 */
public class ArcheryTimeDisplay extends Application {

  private final Logger log = Logger.getLogger("ArcheryTimeDisplay");
  private final static Rectangle2D DEFAULT_SIZE = new Rectangle2D(0.0, 0.0, 1920.0 * 0.5, 1080.0 * 0.5);
  public final static PseudoClass STEADY_STATE = PseudoClass.getPseudoClass("steady");
  public final static PseudoClass SHOOT_UP30_STATE = PseudoClass.getPseudoClass("up30");

  private final StartupViewModel startupViewModel = new StartupViewModel();
  private final CountdownViewModel countdownViewModel = new CountdownViewModel();

  private final Pane rootPane = new StackPane();
  private Node activeScreen;
  private Stage primaryStage;

  private Listener listener;

  private volatile ScreenState screenState;

  private synchronized ScreenState getScreenState() {
    return screenState;
  }


  private static final Map<? super ScreenState.Screen, Class<? extends Pane>> screen2view = createScreenMap();

  private static Map<? super ScreenState.Screen, Class<? extends Pane>> createScreenMap() {
    final Map<ScreenState.Screen, Class<? extends Pane>> map = new EnumMap<>(ScreenState.Screen.class);
    map.put(ScreenState.Screen.SHOOT, CountDownScreen.class);
    map.put(ScreenState.Screen.SHOOT_UP30, CountDownScreen.class);
    map.put(ScreenState.Screen.STEADY, CountDownScreen.class);
    map.put(ScreenState.Screen.STOP, StopScreen.class);
    return map;
  }

  private final Consumer<ScreenState> screenStateConsumer = new Consumer<ScreenState>() {
    @Override
    public void accept(final ScreenState s) {
      screenState = s;
      log.info("UPDATED in application: " + s);
      Platform.runLater(() -> updateUi());
    }
  };

  private void updateUi() {
    switch (getScreenState().getScreen()) {
      case STOP:
        showStop();
        break;
      case STEADY:
        showSteady();
        break;
      case SHOOT:
        showShoot();
        break;
      case SHOOT_UP30:
        showShootUp30();
        break;
      case MESSAGE:
        showMessage(screenState.getMessage());
      default:
        showStartup();
    }
  }


  @Override
  public void init() throws Exception {
    listenForServer();
  }

  private void listenForServer() {
    listener = new Listener(screenStateConsumer);
  }

  @Override
  public void start(final Stage stage) throws Exception {
    primaryStage = stage;

    primaryStage.setOnCloseRequest(e -> {
      listener.stop();
      Platform.exit();
      System.exit(0);
    });

    layout();

    enterFullScreenMode();
    showStartup();
    registerHotKeys();
  }

  private void layout() {
    primaryStage.setWidth(DEFAULT_SIZE.getWidth());
    primaryStage.setHeight(DEFAULT_SIZE.getHeight());

    final Scene rootScene = new Scene(rootPane);
    primaryStage.setScene(rootScene);
    primaryStage.show();

//    primaryStage.widthProperty().addListener(observable -> applyScreenScale());
//    primaryStage.heightProperty().addListener(observable -> applyScreenScale());

    setUserAgentStylesheet(getClass().getResource("display.css").toExternalForm());
  }

  private void applyScreenScale() {
    final double factor = getFactor();

    rootPane.setScaleX(factor);
    rootPane.setScaleY(factor);
  }

  private double getFactor() {
    final Rectangle2D bounds = new Rectangle2D(0, 0, primaryStage.getWidth(), primaryStage.getHeight());

    final double wFactor = bounds.getWidth() / DEFAULT_SIZE.getWidth();
    final double hFactor = bounds.getHeight() / DEFAULT_SIZE.getHeight();
    final double factor = Math.min(wFactor, hFactor);
    System.out.printf("scale factor %10.3f %n", factor);
    return factor;
  }

  private void registerHotKeys() {
    // fullscreen F
    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.F) {
        if (primaryStage.isFullScreen())
          exitFullScreenMode();
        else
          enterFullScreenMode();
        event.consume();
      }
    });

    // exit Ctrl-C
    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.C
          && event.isControlDown()) {
        Platform.exit();
        System.exit(0);
      }
    });
  }


  private void enterFullScreenMode() {
    primaryStage.setFullScreen(true);
  }

  private void exitFullScreenMode() {
    primaryStage.setFullScreen(false);
  }

  private void showMessage(String message) {
    if (isNewScreenInstanceRequired()) {
      activeScreen = new StartupScreen(startupViewModel);
      rootPane.getChildren().setAll(activeScreen);
    }
    startupViewModel.setMessage(message);
  }

  private void showStartup() {
    showMessage("wait for connection");
  }

  private void showStop() {
    if (isNewScreenInstanceRequired()) {
      activeScreen = new StopScreen();
      rootPane.getChildren().setAll(activeScreen);
    }
  }

  private void showCountdown() {
    if (isNewScreenInstanceRequired()) {
      activeScreen = new CountDownScreen(countdownViewModel);
      rootPane.getChildren().setAll(activeScreen);
    }
    countdownViewModel.setCountdown(getScreenState().getSeconds());
    countdownViewModel.setSequence(getScreenState().getSequence());
  }

  private void showSteady() {
    showCountdown();
    activeScreen.pseudoClassStateChanged(STEADY_STATE, true);
    activeScreen.pseudoClassStateChanged(SHOOT_UP30_STATE, false);
  }

  private void showShoot() {
    showCountdown();
    activeScreen.pseudoClassStateChanged(STEADY_STATE, false);
    activeScreen.pseudoClassStateChanged(SHOOT_UP30_STATE, false);
  }

  private void showShootUp30() {
    showCountdown();
    activeScreen.pseudoClassStateChanged(STEADY_STATE, false);
    activeScreen.pseudoClassStateChanged(SHOOT_UP30_STATE, true);
  }

  private boolean isNewScreenInstanceRequired() {
    return activeScreen == null
        || !activeScreen.getClass()
        .equals(screen2view.get(getScreenState().getScreen()));
  }

}
