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

import com.tronner.dispatcher.Commands;
import com.tronner.servers.racing.Racing;
import com.tronner.servers.racing.lang.LMapManager;
import com.tronner.servers.racing.logs.LogManager;
import com.tronner.servers.racing.logs.MapLog;
import com.tronner.servers.racing.logs.PlayerTime;
import com.tronner.util.JsonManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tronner - MapManager
 *
 * @author TJohnW
 */
public class MapManager {

    private Map<String, RacingMap> maps;


    private LogManager logger;

    private MapLog currentLog;

    private QueueManager queue;

    private RotationManager rotation;

    private RoundMapManager currentManager = rotation;

    private RacingMap currentMap;

    /**
     * Creates the MapManager and loads the maps
     */
    public MapManager(LogManager logManager) {
        logger = logManager;
        maps = new HashMap<>();
        loadMaps();
        queue = new QueueManager();
        rotation = new RotationManager(this);
        currentManager = rotation;
    }

    /**
     * Called when a player finishes the current map
     * @param playerTime the PlayerTime
     */
    public void finished(PlayerTime playerTime) {
        int oldRank = currentLog.getRank(playerTime.getPlayer());

        double difference = currentLog.updateRecord(playerTime);

        int newRank = currentLog.getRank(playerTime.getPlayer());

        boolean improvedTime = (difference < 0.0d);

        boolean improvedRank = (newRank > oldRank);

        LMapManager.player_finished(playerTime.getPlayer(), improvedTime, improvedRank, difference, newRank);
    }

    /**
     * Progresses the RoundMapManager to the next map
     * and sets the current RacingMap.
     */
    public void next() {
        if(currentMap != null)
            logger.unloadMapLog(currentMap.getName(), true);

        if(!currentManager.isActive())
            currentManager = rotation; // rotation is never "unactive"

        currentMap = currentManager.next();
        logger.loadMapLog(currentMap.getName());
        currentLog = logger.getLog(currentMap.getName());
    }

    /**
     * ACTUALLY LOADS THE CURRENT MAP INTO THE SERVER!
     */
    public void load() {
        next();
        Commands.MAP_FILE(currentMap.getPath());
        LMapManager.current_map(currentMap);
    }

    /**
     * Gets the Maps loaded
     * @return the maps
     */
    public Map<String, RacingMap> getMaps() {
        return maps;
    }

    /**
     * Gets the Queue
     * @return the QueueManager
     */
    public QueueManager getQueue() {
        return queue;
    }

    /**
     * Gets the Rotation
     * @return the RotationManager
     */
    public RotationManager getRotation() {
        return rotation;
    }

    /**
     * Used to get the current map
     * @return the current RacingMap
     */
    public RacingMap getCurrentMap() {
        return currentMap;
    }

    /**
     * Is the map valid?
     * @param mapName the map name to check
     * @return true if valid
     */
    public boolean validMap(String mapName) {
        return maps.containsKey(mapName);
    }

    /**
     * Initializes the mapManager
     */
    @SuppressWarnings("unchecked")
    public void loadMaps() {
        try {
            List<String> mapsLoad = JsonManager.loadFromJson(Racing.PATH + "data/Maps" + ".JSON", List.class);
            for(String s: mapsLoad) {
                RacingMap rm = new RacingMap(s);
                maps.put(rm.getName(), rm);
            }
        } catch (IOException e) {
            System.out.println("Error loading MapData file at data/Maps.JSON");
        }
    }

}
