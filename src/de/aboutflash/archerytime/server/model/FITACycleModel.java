package de.aboutflash.archerytime.server.model;

import de.aboutflash.archerytime.model.ScreenState;

/**
 * Class
 *
 * @author falk@aboutflash.de on 01.12.2017.
 */
public interface FITACycleModel {
  double getRemainingTimeMillis();

  void setRemainingTimeMillis(double value);

  void decreaseRemainingTime(double milliseconds);

  int getRemainingTimeSeconds();

  ScreenState getScreenState();

}
