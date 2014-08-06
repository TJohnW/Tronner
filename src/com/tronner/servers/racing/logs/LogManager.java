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

package com.tronner.servers.racing.logs;

import com.tronner.servers.racing.Racing;
import com.tronner.util.JsonManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Tronner - LogManager
 *
 * @author TJohnW
 */
public class LogManager {

    private Map<String, MapLog> mapLogs = new HashMap<>();

    public LogManager() {

    }

    /**
     * Gets the log for the map, if not available,
     * attempts to load it from data files.
     * @param map the map to load
     * @return the MapLog
     */
    public MapLog getLog(String map) {
        return mapLogs.get(map);
    }

    /**
     * Sets the log for the map
     * @param map the map to set
     * @param log the MapLog
     */
    public void setLog(String map, MapLog log) {
        mapLogs.put(map, log);
    }

    /**
     * Default behavior for loadMapLog
     * @param mapName the map to load the log for.
     */
    public void loadMapLog(String mapName) {
        loadMapLog(mapName, true);
    }

    /**
     * Loads the MapLog for the given mapName
     * if the log does not exist doesnt add to map
     * @param mapName the map to load the log for.
     */
    public void loadMapLog(String mapName, boolean createOnFail) {
        try {
            mapLogs.put(mapName, JsonManager.loadFromJson(Racing.PATH + "data/maplogs/" + mapName + ".JSON",
                    MapLog.class));
            mapLogs.get(mapName).sort();
        } catch (IOException e) {
            if(!createOnFail) {
                System.out.println("Error loading MapLog, finished");
                return;
            }
            System.out.println("Error loading MapLog for map, attempting to create it: " + mapName);
            MapLog ml = new MapLog(mapName);
            mapLogs.put(mapName, ml);
            saveMapLog(mapName);
        }
    }

    /**
     * Lets unload this log, not needed for now
     * @param mapName the map to forget and let memory manage
     * @param save save the map first?
     */
    public void unloadMapLog(String mapName, boolean save) {
        if(save) saveMapLog(mapName);
        mapLogs.remove(mapName);
    }

    /**
     * Saves the MapLog for the given mapName
     * if the log cannot be saved, ignored
     * @param mapName the MapLog to save from the map
     */
    public void saveMapLog(String mapName) {
        try {
            JsonManager.saveAsJson(Racing.PATH + "data/maplogs/" + mapName + ".JSON", mapLogs.get(mapName));
        } catch (IOException e) {
            System.out.println("Unable to save MapLog for map: " + mapName);
        }
    }

    /**
     * Returns the rank of the player based on the specified map
     *
     * @param map the map to search
     * @param playerId the players registered forum name or in game name (if not logged in)
     * @return the players rank.
     */
    public int getRank(String map, String playerId) {
        if(!mapLogs.containsKey(map)) {
            // lets try loading the map first
            loadMapLog(map, false);
            if(!mapLogs.containsKey(map))
                return -1;
            return getRank(map, playerId);
        }
        return mapLogs.get(map).getRank(playerId);
    }

}
