package de.aboutflash.archerytime.server.net;

import de.aboutflash.archerytime.server.model.FITACycleModel;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Class
 *
 * @author falk@aboutflash.de on 24.11.2017.
 */
public class Announcer {

  private final ExecutorService executorService;
  private final FITACycleModel model;

  public Announcer(final FITACycleModel model) {
    this.model = model;

    executorService = newSingleThreadExecutor();
    runForever();
  }

  public void stop() {
    executorService.shutdownNow();
  }

  private void runForever() {
    try {
      executorService.submit(new AnnounceThread(model));
    } catch (UnknownHostException | SocketException ignored) { }
  }
}
