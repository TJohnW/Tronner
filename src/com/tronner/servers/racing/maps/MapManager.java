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
import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.logs.LogManager;
import com.tronner.servers.racing.players.PlayerManager;
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
public class MapManager extends ServerEventListener {

    private Map<String, RacingMap> maps = new HashMap<>();
    {
        loadMaps();
    }

    private RacingMap currentMap;

    private Queue queue;

    private Rotation rotation = new Rotation(this);

    private RoundMapManager currentManager = rotation;

    /**
     * Creates the MapManager and loads the maps
     */
    public MapManager(PlayerManager pm) {
        Parser.getInstance().reflectListeners(this);
        queue = new Queue(this, pm);
    }

    /**
     * Gets the Maps loaded
     * @return the maps
     */
    public Map<String, RacingMap> getMaps() {
        return maps;
    }

    /**
     * Gets the specified map
     * @param map the map to get
     * @return the RacingMap
     */
    public RacingMap getMap(String map) {
        return maps.get(map);
    }

    public void setCurrentManager(RoundMapManager manager) {
        if(manager.isActive())
            currentManager = manager;
        else
            System.out.println("Tried to set current manager to inactive manager?");
    }

    /**
     * Gets the Queue
     * @return the Queue
     */
    public Queue getQueue() {
        return queue;
    }

    /**
     * Gets the Rotation
     * @return the Rotation
     */
    public Rotation getRotation() {
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
            List<String> mapsLoad = JsonManager.loadFromJson("data/maps" + ".JSON", List.class);
            for(String s: mapsLoad) {
                RacingMap rm = new RacingMap(s);
                maps.put(rm.getName(), rm);
            }
        } catch (IOException e) {
            System.out.println("Error loading MapData file at data/maps.JSON");
        }
    }

    /**
     * ACTUALLY LOADS THE CURRENT MAP INTO THE SERVER!
     *
     * Progresses the RoundMapManager to the next map
     * and sets the current RacingMap.
     */
    @Override
    public void ROUND_COMMENCING() {
        if(currentMap != null)
            LogManager.getInstance().unloadMapLog(currentMap.getName(), true);

        if(!currentManager.isActive())
            currentManager = rotation; // rotation is never "unactive"

        currentMap = currentManager.next();
        LogManager.getInstance().loadMapLog(currentMap.getName());
        LogManager.getInstance().setCurrentLog(LogManager.getInstance().getLog(currentMap.getName()));
        Commands.MAP_FILE(currentMap.getPath());
    }

}
