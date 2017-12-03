package de.aboutflash.archerytime.remoteclient.net;

import de.aboutflash.archerytime.model.ScreenState;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Class
 *
 * @author falk@aboutflash.de on 24.11.2017.
 */
public final class Listener {

  private final ExecutorService executorService;
  private final Consumer<ScreenState> screenStateConsumer;

  public Listener(final Consumer<ScreenState> screenStateConsumer) {
    this.screenStateConsumer = screenStateConsumer;
    executorService = newSingleThreadExecutor();
    runForever();
  }

  public void stop() {
    executorService.shutdownNow();
  }

  private void runForever() {
    try {
      executorService.submit(new ListenerThread(screenStateConsumer));
    } catch (UnknownHostException | SocketException ignored) { }
  }

}
