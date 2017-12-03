package de.aboutflash.archerytime.server.main;

import de.aboutflash.archerytime.server.model.ControlViewModel;
import de.aboutflash.archerytime.server.model.FITACycleModel;
import de.aboutflash.archerytime.server.model.FITACycleModelSimulation;
import de.aboutflash.archerytime.server.net.Announcer;
import de.aboutflash.archerytime.server.ui.ControlScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class
 *
 * @author falk@aboutflash.de on 19.11.2017.
 */
public class ArcheryTimeControlServer extends Application {

    private final static Rectangle2D DEFAULT_SIZE = new Rectangle2D(0.0, 0.0, 1920.0 * 0.33, 1080.0 * 0.33);

    private Stage primaryStage;
    private StackPane rootPane;
    private ControlViewModel controlViewModel;

    private Announcer announcer;
    private FITACycleModel model;

    public static void main(final String... args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        model = new FITACycleModelSimulation();
        announceServer();
        observeModel();
    }

    private void announceServer() {
        announcer = new Announcer(model);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        primaryStage = stage;

        primaryStage.setOnCloseRequest(e -> {
            announcer.stop();
            Platform.exit();
            System.exit(0);
        });

        layout();
        registerHotKeys();
        showControlScreen();
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
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event ->

        {
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


    private void layout() {
        setUserAgentStylesheet(getClass().getResource("server.css").toExternalForm());

        primaryStage.setWidth(DEFAULT_SIZE.getWidth());
        primaryStage.setHeight(DEFAULT_SIZE.getHeight());

        rootPane = new StackPane();
        primaryStage.setScene(new Scene(rootPane));

        primaryStage.show();
    }

    private void showControlScreen() {
        controlViewModel = new ControlViewModel();
        rootPane.getChildren().setAll(new ControlScreen(controlViewModel));
    }

    // for debugging

    private void observeModel() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateUi());
            }
        }, 0, 100);
    }

    private void updateUi() {
        if (controlViewModel != null)
            controlViewModel.setStatus(model.getScreenState().toString());
    }

}
