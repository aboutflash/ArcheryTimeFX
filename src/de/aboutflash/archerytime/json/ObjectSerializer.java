package de.aboutflash.archerytime.json;

import de.aboutflash.archerytime.model.ScreenState;


public interface ObjectSerializer {

  String serializeScreenState(ScreenState screenState);

  ScreenState deserializeScreenState(String json);
}
