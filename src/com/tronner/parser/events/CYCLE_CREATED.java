package com.tronner.parser.events;

import com.tronner.parser.ServerEvent;
import com.tronner.parser.ServerEventListener;

/**
 * The class to represent the GAME_TIME command
 *
 * @author TJohnW
 */
public class CYCLE_CREATED extends ServerEvent {

    @Override
    public void onEvent(ServerEventListener sel, String... args) {
        sel.CYCLE_CREATED(args[0], Float.parseFloat(args[1]), Float.parseFloat(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
    }
}
