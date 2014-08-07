/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Tristan John Whitcher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tronner.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Tronner - ServerEvent
 *
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

    public int i(String arg) {
        return Integer.parseInt(arg);
    }

    public float f(String arg) {
        return Float.parseFloat(arg);
    }

    /**
     * Handled by all of the ServerEvent extended classes for ladderlog events.
     * @param il the InputListener to call the command on
     * @param args the arguments passed after the command
     */
    public abstract void onEvent(ServerEventListener il, String... args);

}
