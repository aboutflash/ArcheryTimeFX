package de.aboutflash.archerytime.server.net;

import de.aboutflash.archerytime.json.JSONObjectSerializer;
import de.aboutflash.archerytime.net.TransmissionThread;
import de.aboutflash.archerytime.server.model.FITACycleModel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Class
 *
 * @author falk@aboutflash.de on 25.11.2017.
 */
@SuppressWarnings("WeakerAccess")
public class AnnounceThread extends TransmissionThread {

  private static final long ANNOUNCE_INTERVAL_MILLIS = 200L;
  private final Logger log;

  private final JSONObjectSerializer serializer;
  private final FITACycleModel model;

  private long announcementCount;

  public AnnounceThread(final FITACycleModel model) throws UnknownHostException, SocketException {
    this.model = model;
    serializer = new JSONObjectSerializer();
    log = Logger.getLogger(getClass().getName());
  }


  @Override
  public void run() {
    announceServer();
  }

  @SuppressWarnings({"BusyWait", "NestedTryStatement", "InfiniteLoopStatement", "Duplicates"})
  private void announceServer() {

    try (DatagramSocket socket = new DatagramSocket()) {
      socket.setBroadcast(true);


      do {
        try {
          byte[] sendData = getSerializedData().getBytes();
          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, getSubnetAddress(), RESPONSE_PORT);

          //Send server announcement
          socket.send(sendPacket);
          announcementCount++;
          log.info(toString());

          sleep(ANNOUNCE_INTERVAL_MILLIS);

        } catch (IOException | InterruptedException e) {
          log.severe(e.getMessage());
        }

      } while (true);

    } catch (SocketException e) {
      log.severe(e.getMessage());
    }

  }

  @Override
  public String toString() {
    return String.format("%nSender: announced server %12d times", announcementCount);
  }

  private String getSerializedData() {
    return serializer.serializeScreenState(model.getScreenState());
  }
}
