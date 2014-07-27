package com.tronner.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TJohnW
 */
public abstract class ServerEvent {

    /**
     * The list of listeners for the event.
     */
    protected List<ServerEventListener> listeners = new ArrayList<>();

    /**
     * Adds a listener to this server input.
     * @param il the ServerEventListener to add
     */
    public void addListener(ServerEventListener il) {
        listeners.add(il);
    }

    /**
     * Removes a listener from this server input.
     * @param il the ServerEventListener to remove
     */
    public void removeListener(ServerEventListener il) {
        listeners.remove(il);
    }

    /**
     * Called by the parser to dispatch the event to every listener.
     * @param args the arguments passed after the command
     */
    public void onEvent(String... args) {
        for(ServerEventListener il: listeners)
            onEvent(il, args);
    }

    /**
     * Handled by all of the ServerEvent extended classes for ladderlog events.
     * @param il the InputListener to call the command on
     * @param args the arguments passed after the command
     */
    public abstract void onEvent(ServerEventListener il, String... args);

}
