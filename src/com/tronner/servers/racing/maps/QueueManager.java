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
 * Tronner - QueueManager
 *
 * @author TJohnW
 */
public class QueueManager implements RoundMapManager {

    private boolean active = false;

    private boolean enabled = false;

    private LinkedList<String> queue = new LinkedList<>();

    public void addAt(int index, String mapName) {
        queue.add(index, mapName);
    }

    public void add(String mapName) {
        queue.addLast(mapName);
    }

    public void removeAt(int index) {
        queue.remove(index);
    }

    public void remove(String mapName) {
        while(queue.contains(mapName))
            queue.remove(mapName);
    }

    public void start() throws QueueEmptyException, QueueDisabledException {
        if(!enabled)
            throw new QueueDisabledException();

        else if(queue.size() == 0)
            throw new QueueEmptyException();

        active = true;
    }

    public boolean validMap(String mapName) {
        return false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String next() {
        return queue.pop();
    }

    public class QueueEmptyException extends Exception {}

    public class QueueDisabledException extends Exception {}
}
