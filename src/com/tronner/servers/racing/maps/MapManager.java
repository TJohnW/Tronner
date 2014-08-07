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

    private Map<String, RacingMap> maps = new HashMap<>();

    private QueueManager queue = new QueueManager();

    private RotationManager rotation = new RotationManager(this);

    private RoundMapManager currentManager = rotation;

    private RacingMap currentMap;

    /**
     * Creates the MapManager and loads the maps
     */
    public MapManager() {
        loadMaps();
    }

    /**
     * Progresses the RoundMapManager to the next map
     * and sets the current RacingMap.
     */
    public void next() {
        if(currentManager.isActive())
            currentMap = currentManager.next();
        else {
            currentManager = rotation; // rotation is never "unactive"
        }
    }

    /**
     * ACTUALLY LOADS THE CURRENT MAP INTO THE SERVER!
     */
    public void load() {
        Commands.MAP_FILE(currentMap.getPath());
        //LMapManager.outputMapData();
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
