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
     * Adds a listener to this server event.
     * @param sel the ServerEventListener to add
     */
    public void addListener(ServerEventListener sel) {
        listeners.add(sel);
    }

    /**
     * Removes a listener from this server event.
     * @param sel the ServerEventListener to remove
     */
    public void removeListener(ServerEventListener sel) {
        listeners.remove(sel);
    }

    /**
     * Called by the parser to dispatch the event to every listener.
     * @param args the arguments passed after the command
     */
    public void onEvent(String... args) {
        for(ServerEventListener sel: listeners)
            onEvent(sel, args);
    }

    /**
     * Handled by all of the ServerEvent extended classes for ladderlog events.
     * @param sel the ServerEventListener to call the command on
     * @param args the arguments passed after the command
     */
    public abstract void onEvent(ServerEventListener sel, String... args);

}
