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

import com.tronner.Application;
import com.tronner.dispatcher.Commands;
import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.logs.LogManager;
import com.tronner.servers.racing.maps.MapManager;
import com.tronner.servers.racing.players.PlayerManager;

/**
 * Tronner - Racing
 *
 * @author TJohnW
 */
@SuppressWarnings("ALL")
public class Racing extends ServerEventListener {

    public static final String PATH = "";

    private LogManager logger = new LogManager();

    private MapManager mapManager = new MapManager(logger);

    private PlayerManager playerManager = new PlayerManager();

    private RaceTimer timer = new RaceTimer(playerManager);

    /**
     * Kills all players to end current round on startup.
     */
    public Racing() {
        Commands.CYCLE_RUBBER(-10);
        Application.sleep(1000);
        Commands.CYCLE_RUBBER(90);

        Parser p = Parser.getInstance(this);

        // sets the order of precedence for the event listeners

        p.reflectListeners(playerManager);

        p.reflectListeners(timer);

        p.reflectListeners(mapManager);

        p.reflectListeners(logger);

        p.reflectListeners(this);
    }

}
