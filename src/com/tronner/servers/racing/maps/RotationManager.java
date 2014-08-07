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

import com.tronner.servers.racing.logs.PlayerTime;
import com.tronner.util.JsonManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Tronner - RoundManager
 *
 * @author TJohnW
 */
public class RotationManager implements RoundMapManager {

    /**
     * The currentIndex in the rotation
     */
    private int currentIndex = 0;

    /**
     * The List of maps for the rotation to use
     */
    private List<RacingMap> maps = new ArrayList<>(100);

    /**
     * Constructs a RotationManager for the given MapManager
     * @param mm the MapManager to use
     */
    public RotationManager(MapManager mm) {
        updateFromManager(mm);
    }

    /**
     * Updates the rotation to match
     * the maps in the MapManager
     */
    public void updateFromManager(MapManager mapManager) {
        maps.addAll(mapManager.getMaps().values());
        Collections.sort(maps, new Comparator<RacingMap>() {
            @Override
            public int compare(RacingMap o1, RacingMap o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public RacingMap next() {
        if(currentIndex == maps.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        return maps.get(currentIndex);
    }
}
