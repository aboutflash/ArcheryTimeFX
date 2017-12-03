package de.aboutflash.archerytime.server.model;

import de.aboutflash.archerytime.model.ScreenState;
import javafx.collections.ObservableList;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleModelSimulation implements FITACycleModel {

  private static final double MILLIS_TO_SECONDS = 0.001;

  private final static double SIM_ROUND_TIME = 20_000.0;

  private final AtomicInteger iterations = new AtomicInteger(0);

  private final TimerTask simulationTask;
  private final Timer simulationTimer;

  public FITACycleModelSimulation() {
    simulationTimer = new Timer();

    simulationTask = new TimerTask() {
      @Override
      public void run() {
        iterations.addAndGet(1);

        decreaseRemainingTime(100);

        System.out.print('.');

        if (remainingTimeMillis <= 0.0) {
          remainingTimeMillis = SIM_ROUND_TIME;
          randomScreen = getRandomScreen();
          randomSequence = getRandomSequence();
        }
      }
    };

    simulationTimer.scheduleAtFixedRate(simulationTask, 1_000, 100);
  }

  private volatile double remainingTimeMillis = SIM_ROUND_TIME;

  @Override
  public synchronized double getRemainingTimeMillis() {
    return remainingTimeMillis;
  }

  @Override
  public synchronized void setRemainingTimeMillis(double value) {
    remainingTimeMillis = value;
  }

  @Override
  public synchronized void decreaseRemainingTime(double milliseconds) {
    remainingTimeMillis -= milliseconds;
  }

  @Override
  public int getRemainingTimeSeconds() {
    return (int) Math.floor(getRemainingTimeMillis() * MILLIS_TO_SECONDS);
  }

  private volatile ScreenState.Screen randomScreen = getRandomScreen();

  private ScreenState.Screen getRandomScreen() {
    final ObservableList<ScreenState.Screen> screens = observableArrayList(
        ScreenState.Screen.STOP,
        ScreenState.Screen.STEADY,
        ScreenState.Screen.SHOOT,
        ScreenState.Screen.SHOOT_UP30
    );

    final int idx = (int) Math.floor(Math.random() * (double) (screens.size()));
    return screens.get(idx);
  }

  private volatile ScreenState.Sequence randomSequence = getRandomSequence();

  private ScreenState.Sequence getRandomSequence() {
    final ObservableList<ScreenState.Sequence> sequences = observableArrayList(
        ScreenState.Sequence.A,
        ScreenState.Sequence.B,
        ScreenState.Sequence.AB,
        ScreenState.Sequence.CD
    );

    final int idx = (int) Math.floor(Math.random() * (double) (sequences.size()));
    return sequences.get(idx);
  }


  @Override
  public synchronized ScreenState getScreenState() {
    final ScreenState screenState = new ScreenState(randomScreen, randomSequence, getRemainingTimeSeconds());
    return screenState;
  }


}
