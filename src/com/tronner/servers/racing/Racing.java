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

import com.sun.media.sound.RIFFInvalidDataException;
import com.tronner.Application;
import com.tronner.dispatcher.Commands;
import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.lang.LColors;
import com.tronner.servers.racing.logs.LogManager;
import com.tronner.servers.racing.maps.MapManager;
import com.tronner.servers.racing.players.PlayerManager;

import java.util.*;

/**
 * Tronner - Racing
 *
 * @author TJohnW
 */
@SuppressWarnings("ALL")
public class Racing extends ServerEventListener {

    public static final String PATH_TIMES = "times/";

    public static final int MAP_PLAYS = 2;

    private PlayerManager playerManager;

    private RaceTimer timer;

    private LogManager logger;

    private MapManager mapManager;

    private Map<String, String> jokesters = new HashMap<String, String>()
    {{
        put("rawrica@forums", "0x55bbff|0xffffffErica");
        put("TJohnW@forums", "0xf0e090Tristan");
        put("alekzander@forums", "0x779977|0xffffffEk");
        put("Zwazi@aagid", "0x00ff88|0xffffffZwazi");
    }};



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

        logger = new LogManager(playerManager);

        mapManager = new MapManager(playerManager, logger, timer);

        new AFKKiller(playerManager, timer);

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
    public void INVALID_COMMAND(String... args) {
        //[/q, TJohnW@forums, 76.185.188.37, -2, add, Telepo]
        System.out.println(Arrays.toString(args));
        if(args[0].equals("/r") && jokesters.containsKey(args[1])) {
            String message = "";
            for(int i = 4; i < args.length; i++) {
                message += " " + args[i];
            }

            String reversed = new StringBuilder(message).reverse().toString();

            Commands.CONSOLE_MESSAGE(jokesters.get(args[1]) + LColors.CHAT_COLOR + ": " + reversed);
            for(String name: jokesters.keySet()) {
                if(!name.equals(args[1])) {
                    Commands.PLAYER_MESSAGE(name, "Jokes.us from " + jokesters.get(args[1]) + LColors.CHAT_COLOR + ":" + message);
                }
            }
        } else if(args[0].equals("/clearplayers") && jokesters.containsKey(args[1])) {
            playerManager.reset();
        } else if(args[0].equals("/goto") && jokesters.containsKey(args[1]) && args.length > 4) {
            try {
                int indexToGo = Integer.parseInt(args[4]);
                mapManager.getRotation().goTo(indexToGo);
            } catch(NumberFormatException nfe) {

            }
        }
    }

    

}
