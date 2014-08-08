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

package com.tronner.servers.racing.maps;

import java.util.LinkedList;

/**
 * Tronner - Queue
 *
 * @author TJohnW
 */
public class Queue implements RoundMapManager {

    /**
     * True if the queue is active
     */
    private boolean active = false;

    /**
     * True if the queue is enabled by the owners
     */
    private boolean enabled = false;

    /**
     * The queue of maps stored as a LinkedList
     */
    private LinkedList<RacingMap> queue = new LinkedList<>();

    /**
     * Creates a new Queue with the given MapManager
     */
    public Queue() { }

    /**
     * Adds to the queue at a certain index
     * @param index the index to add
     * @param map the name of the map to add
     */
    public void addAt(int index, RacingMap map) {
        queue.add(index, map);
    }

    /**
     * Adds to the end of the queue
     * @param map the name of the map to add
     */
    public void add(RacingMap map) {
        queue.addLast(map);
    }

    /**
     * Removes from a certain index
     * @param index the index to remove from
     */
    public void removeAt(int index) {
        queue.remove(index);
    }

    /**
     * Removes a given map from the queue anywhere, and all of them
     * @param map the map to remove
     */
    public void remove(RacingMap map) {
        while(queue.contains(map))
            queue.remove(map);
    }

    /**
     * Attempts to start the queue and activate it
     * @throws QueueEmptyException if the queue is empty
     * @throws QueueDisabledException if the queue is disable by +admin
     */
    public void start() throws QueueEmptyException, QueueDisabledException {
        if(!enabled)
            throw new QueueDisabledException();

        else if(queue.size() == 0)
            throw new QueueEmptyException();

        active = true;
    }

    @Override
    public boolean isActive() {
        if(queue.size() == 0) {
            active = false;
            return false;
        }
        return active;
    }

    @Override
    public RacingMap next() {
        if(queue.size() != 0) {
            return queue.pop();
        } else {
            return null;
        }
    }

    /**
     * Thrown when the queue is empty and tried to start
     */
    public class QueueEmptyException extends Exception {}

    /**
     * Thrown when the queue is disabled and tried to start
     */
    public class QueueDisabledException extends Exception {}
}
