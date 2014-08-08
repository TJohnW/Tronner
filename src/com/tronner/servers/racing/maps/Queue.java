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

import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.players.PlayerManager;

import java.util.LinkedList;

/**
 * Tronner - Queue
 *
 * @author TJohnW
 */
public class Queue extends ServerEventListener implements RoundMapManager {

    private MapManager mapManager;

    private PlayerManager playerManager;

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
    public Queue(MapManager mm, PlayerManager pm) {
        Parser.getInstance().reflectListeners(this);
        mapManager = mm;
        playerManager = pm;
    }

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
     * Called to clear the queue
     */
    public void clear() {
        queue = new LinkedList<>();
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
        mapManager.setCurrentManager(this);
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

    @Override
    public void INVALID_COMMAND(String... args) {
        if(!"/q".equals(args[0]) && !"/queue".equals(args[0]))
            return;

        String player = args[1];
        String ip = args[2];

        int accessLevel = 20;
        try {
            accessLevel = Integer.parseInt(args[3]);
        } catch(NumberFormatException nfe) {
            System.out.println("Odd.. Access number wasn't a number?");
        }

        String action = "";

        if(args.length > 4)
            action = args[4];

        switch(action) {
            case "add":

                if(args.length < 6)
                    // error no map specified
                    return;
                qAdd(player, accessLevel, args[5]);
                break;

            case "remove":

                if(args.length < 6)
                    // error no index specified
                    return;
                qRemove(player, accessLevel, args[5]);
                break;

            case "clear":

                qClear(player, accessLevel);
                break;

            case "start":

                qStart(player, accessLevel);
                break;

            case "stop":

                qStop(player, accessLevel);
                break;

            case "enable":

                qEnable(player, accessLevel);
                break;

            case "disable":

                qDisable(player, accessLevel);
                break;

            default:

                break;
        }
    }

    /**
     * Called to parse a command for enabling the queue
     * @param player the player who requested to enable
     * @param accessLevel the accesslevel of the player
     */
    private void qEnable(String player, int accessLevel) {
        if(accessLevel > 0) {
            // error no access
        } else if(enabled) {
            // error already enabled
        } else {
            // success
            enabled = true;
        }
    }

    /**
     * Called to parse a command for disabling the queue
     * @param player the player who requested to disable the queue
     * @param accessLevel the accesslevel of the player
     */
    private void qDisable(String player, int accessLevel) {
        if(accessLevel > 0) {
            // error no access
        } else if(!enabled) {
            // error already disabled
        } else {
            // success
            enabled = false;
        }
    }

    /**
     * Called to parse the queue command for add
     * @param player the player who requested an add
     * @param accessLevel the accesslevel of the player
     * @param map the map they wish to add
     */
    private void qAdd(String player, int accessLevel, String map) {
        if(!mapManager.validMap(map)) {
            // error no map found..
        } else if(playerManager.playerFromID(player).getQueues() <= 0 && accessLevel > 0) {
            // error out of queues
        } else {
            // success
            add(mapManager.getMap(map));
        }
    }

    /**
     * Called to parse the queue command for remove
     * @param player the player who requested to remove
     * @param accessLevel the accesslevel of the player
     * @param index the index they wish to remove
     */
    private void qRemove(String player, int accessLevel, String index) {
        int removeIndex;

        try {
            removeIndex = Integer.parseInt(index);
        } catch(NumberFormatException nfe) {
            // error not an index
            return;
        }

        if(accessLevel > 0) {
            // error does not have ability to remove
        } else if(queue.size() <= removeIndex) {
            // error index does not exist
        } else {
            removeAt(removeIndex);
        }
    }

    /**
     * Called to parse the queue command for clear
     * @param player the player who requested to clear
     * @param accessLevel the accesslevel of the player
     */
    private void qClear(String player, int accessLevel) {
        if(accessLevel > 0) {
            // error no access
        } else {
            clear();
        }
    }

    /**
     * Called when a player starts the queue
     * @param player the player who requested to start
     * @param accessLevel the accesslevel of the player
     */
    private void qStart(String player, int accessLevel) {
        try {
            if(!active) {
                start();
                // success
            }
        } catch(QueueDisabledException qde) {
            // error queue disabled by admin.
        } catch(QueueEmptyException qee) {
            // error queue empty
        }
    }

    /**
     * Called to parse a queue command for stop
     * @param player the player who requested to stop
     * @param accessLevel the accesslevel of the player
     */
    private void qStop(String player, int accessLevel) {
        if(accessLevel > 0) {
            // error no access
        } else if(!isActive()) {
            // error already not active
        } else {
            // success
            active = false;
        }
    }

}
