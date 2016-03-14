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
import com.tronner.servers.racing.lang.LColors;
import com.tronner.servers.racing.logs.Logger;
import com.tronner.servers.racing.maps.MapManager;
import com.tronner.servers.racing.players.PlayerTracker;
import com.tronner.servers.racing.rankings.Rankings;
import com.tronner.util.Crayola;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Tronner - Racing
 *
 * @author TJohnW
 */
@SuppressWarnings("ALL")
public class Racing extends ServerEventListener {

    public static final String PATH_TIMES = "times/";

    public static final int MAP_PLAYS = 2;

    private PlayerTracker playerTracker;

    private RaceTimer timer;

    private Logger logger;

    private MapManager mapManager;

    private Rankings rankings;

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

        playerTracker = new PlayerTracker(); // Plan to make this PlayerManager and RacerManager extends PlayerManager

        timer = new RaceTimer(playerTracker);

        logger = new Logger(playerTracker);

        mapManager = new MapManager(playerTracker, logger, timer);

        rankings = new Rankings(playerTracker, mapManager);

        new AFKKiller(playerTracker, timer);

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
        Commands.out("CYCLE_RUBBER -90");
        Application.sleep(500);
        Commands.out("CYCLE_RUBBER 90");
    }

    @Override
    public void ROUND_COMMENCING() {
        if(!logger.isRankingsUpdated())
            rankings.threadedUpdate();
        logger.setRankingsUpdated(true);
    }

    @Override
    public void INVALID_COMMAND(String... args) {
        //[/q, TJohnW@forums, 76.185.188.37, -2, add, Telepo]
        // Admin Commands
    }


    public void debug() {

        int mb = 1024*1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        System.out.println("##### Heap utilization statistics [MB] #####");

        //Print used memory
        System.out.println("# Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        System.out.println("# Free Memory:"
                + runtime.freeMemory() / mb);

        //Print total available memory
        System.out.println("# Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        System.out.println("# Max Memory:" + runtime.maxMemory() / mb);

    }
    

}
