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

package com.tronner.servers.racing;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        /**
        MapLog ml = new MapLog("Aflac");

        for(int i = 0; i < 1700; i++) {
            ml.updateRecord(new PlayerTime("Smart", 12 + i/100));
        }

        ml.sort();
        setLog("Aflac", ml);
        saveMapLog("Aflac");
        */
        loadMapLog("Telepo");

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
     * Loads the MapLog for the given mapName
     * if the log does not exist doesnt add to map
     * @param mapName the map to load the log for.
     */
    public void loadMapLog(String mapName) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(Racing.path() + "data/" + mapName + ".JSON"));
            Gson g = new Gson();
            mapLogs.put(mapName, g.fromJson(new String(encoded, StandardCharsets.UTF_8), MapLog.class));
        } catch (IOException e) {
            System.out.println("Error loading MapLog for map, attempting to create it: " + mapName);
            MapLog ml = new MapLog(mapName);
            mapLogs.put(mapName, ml);
            saveMapLog(mapName);
        }
    }

    /**
     * Saves the MapLog for the given mapName
     * if the log cannot be saved, ignored
     * @param mapName the MapLog to save from the map
     */
    public void saveMapLog(String mapName) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(new File(Racing.path() + "data/" + mapName + ".JSON")));
            //Gson g = new GsonBuilder().setPrettyPrinting().create();
            Gson g = new Gson();
            output.write(g.toJson(mapLogs.get(mapName)));
            output.close();
        } catch (IOException e) {
            System.out.println("Unable to save MapLog for map: " + mapName);
        }
    }

    /**
     * Returns the rank of the player based on the list for the
     * specified map.
     * ACCOUNTS FOR INDEXES STARTING AT 0!
     * @param map the map to search
     * @param playerId the player to find
     * @return the rank of the player
     */
    public int getRank(String map, Player playerId) {
        return getRank(map, playerId.getId());
    }

    /**
     * Returns the rank of the player based on the specified map
     *
     * @param map the map to search
     * @param playerId the players registered forum name or in game name (if not logged in)
     * @return the players rank.
     */
    public int getRank(String map, String playerId) {
        if(!mapLogs.containsKey(map))
            return -1;
        return mapLogs.get(map).getRank(playerId);
    }

}
