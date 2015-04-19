package com.rmi.server;

import com.rmi.game.Player;
import lombok.Value;

/**
 * Created by novy on 19.04.15.
 */

@Value
public class GameTookPlaceEvent {

    private final Player playerOne;
    private final Player playerTwo;
}
