package com.tronner.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TJohnW
 */
public abstract class ServerEvent {

    protected List<ServerEventListener> listeners = new ArrayList<>();

    public void addListener(ServerEventListener sel) {
        listeners.add(sel);
    }

    public void removeListener(ServerEventListener sel) {
        listeners.remove(sel);
    }

    public void onEvent(String... args) {
        for(ServerEventListener sel: listeners)
            onEvent(sel, args);
    }

    public abstract void onEvent(ServerEventListener sel, String... args);

}
