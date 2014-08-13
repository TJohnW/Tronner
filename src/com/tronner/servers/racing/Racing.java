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

import java.util.Arrays;

/**
 * Tronner - Racing
 *
 * @author TJohnW
 */
@SuppressWarnings("ALL")
public class Racing extends ServerEventListener {

    public static final String PATH_TIMES = "times/";

    private PlayerManager playerManager;

    private RaceTimer timer;

    private LogManager logger;

    private MapManager mapManager;

    /**
     * Initializes all of the listeners
     * with the proper order for execution.
     */
    public Racing() {
        initialize();

        /**
         * Warning, this order of object creation
         * is IMPORTANT
         * changing the order in which
         * these objects are created could
         * mess up the racing script.
         * Please do not change these until you
         * completely understand the way
         * events are reflected to their
         * listeners.
         */

        playerManager = new PlayerManager(); // Plan to make this PlayerManager and RacerManager extends PlayerManager

        timer = new RaceTimer(playerManager);

        // This is a singleton because it is needed in multiple managers,
        // might think of a better idea, but for now I dont want to restructure
        // again...
        logger = LogManager.getInstance();

        mapManager = new MapManager(playerManager);

        Parser.getInstance().reflectListeners(this);

    }

    /**
     * Kills the currently alive players to not mess up the player engine
     * on script load.
     */
    public void initialize() {
        Commands.CENTER_MESSAGE("Intializing script. Tronner Racing. 0xff98f9:D");
        Commands.out("DELAY_COMMAND 0 SPAWN_ZONE death 0 0 70000");
        Commands.out("DELAY_COMMAND +0 SPAWN_ZONE death 0 0 70000");
    }

    @Override
    public void INVALID_COMMAND(String... args) {
        //[/q, TJohnW@forums, 76.185.188.37, -2, add, Telepo]
        System.out.println(Arrays.toString(args));
    }

}
