package de.aboutflash.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * A utility class to detect the most likely main subnet the current machine communicates
 * with the outside world.
 * <p>
 * Returns only correct values for a /24 subnet.
 *
 * @author falk@aboutflash.de on 25.11.2017.
 */
public final class InetSubnetDetector {

  @SuppressWarnings("ImplicitNumericConversion")
  private static final byte[] UNUSED_ADDRESS = new byte[]{1, 1, 1, 1};
  private static final int FULL_SEGMENT = 0xff;

  private InetSubnetDetector() {}

  /**
   * Gets the broadcast address of the local subnet.
   */
  public static InetAddress getLocalAddress() throws UnknownHostException, SocketException {
    return InetAddress.getByAddress(discoverBroadcastAddress().getAddress());
  }

  /**
   * Gets the broadcast address of the local subnet.
   */
  public static InetAddress getSubnetBroadcastAddress() throws UnknownHostException, SocketException {
    return InetAddress.getByAddress(getBroadcastAddress());
  }

  /**
   * Takes the discovered local address and uses this to determine the broadcast address
   * in the form of xxx.xxx.xxx.255.
   */
  private static byte[] getBroadcastAddress() throws SocketException, UnknownHostException {
    final byte[] address = discoverBroadcastAddress().getAddress();

    // replace last IP segment by .255
    //noinspection NumericCastThatLosesPrecision
    address[3] = (byte) FULL_SEGMENT;

    return address;
  }

  /**
   * Sends an UDP packet to Nirvana and retrieves from the created DatagramSocket the local
   * IPv4 address that was used. This is probably the machines IP address which can be accepted
   * as the "default" network interface address.
   */
  private static InetAddress discoverBroadcastAddress() throws SocketException, UnknownHostException {
    try (DatagramSocket s = new DatagramSocket()) {
      s.connect(InetAddress.getByAddress(UNUSED_ADDRESS), 0);
      return s.getLocalAddress();
    }
  }

}
