package com.tronner.parser.events;

import com.tronner.parser.ServerEvent;
import com.tronner.parser.ServerEventListener;

/**
 * The class to represent the GAME_TIME command
 *
 * @author TJohnW
 */
public class PLAYER_RENAMED extends ServerEvent {

    @Override
    public void onEvent(ServerEventListener sel, String... args) {
        sel.PLAYER_RENAMED(args[0], args[1]);
    }
}
