package com.tronner.parser.events;

import com.tronner.parser.ServerEvent;
import com.tronner.parser.ServerEventListener;

/**
 * The class to represent the GAME_TIME command
 *
 * @author TJohnW
 */
public class GAME_TIME extends ServerEvent {

    @Override
    public void onEvent(ServerEventListener sel, String... args) {
        sel.GAME_TIME(Integer.parseInt(args[0]));
    }
}
