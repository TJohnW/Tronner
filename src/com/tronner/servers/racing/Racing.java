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

import com.tronner.dispatcher.Commands;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.logs.LogManager;
import com.tronner.servers.racing.logs.PlayerTime;
import com.tronner.servers.racing.maps.MapManager;
import com.tronner.servers.racing.maps.QueueManager;
import com.tronner.servers.racing.maps.RotationManager;
import com.tronner.servers.racing.maps.RoundMapManager;

/**
 * Tronner - Racing
 *
 * @author TJohnW
 */
public class Racing extends ServerEventListener {

    public static final String PATH = "";

    public LogManager logger = new LogManager();

    private MapManager manager = new MapManager();

    @Override
    public void GAME_TIME(int time) {
        System.out.println("Time!!!");
    }

    @Override
    public void CYCLE_CREATED(String playerName, float xPosition, float yPosition, int xDirection, int yDirection) {
        System.out.println("A cycle was created at x= " + xPosition + " y= " + yPosition);
    }

    @Override
    public void ROUND_COMMENCING() {
        manager.load();
        logger.getLog(manager.getCurrentMap().getName());
    }

    @Override
    public void TARGETZONE_PLAYER_ENTER(int globalID, float zoneX, float zoneY,
                                        String playerId, float playerX, float playerY, float playerXDir,
                                        float playerYDir, float time) {
        logger.getLog(manager.getCurrentMap().getName()).updateRecord(new PlayerTime(playerId, time));
        Commands.CONSOLE_MESSAGE("Current Map: " + manager.getCurrentMap().getName());
        Commands.CONSOLE_MESSAGE("You finished in " + time + ". Your rank is now " + logger.getLog(manager.getCurrentMap().getName()).getRank(playerId));
    }


}
