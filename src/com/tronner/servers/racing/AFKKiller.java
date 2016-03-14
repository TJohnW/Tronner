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
import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.players.PlayerTracker;
import com.tronner.util.TronLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Tronner - AFKKiller
 *
 * @author Tristan on 8/17/2014.
 */
public class AFKKiller extends ServerEventListener {

    public static final int STRIKES = 5;
    public static final int MIN_DISTANCE = 5;

    private static class AFK {

        private String player;
        private TronLocation loc;
        private int strikes;

        public AFK(String playerID, TronLocation location) {
            player = playerID;
            loc = location;
            strikes = 0;
        }

        /**
         * Ticks the afk check,
         * if they are killed returns true to let the killer remove them from the list so they arent updated again.
         *
         * @param location the location to tick to
         * @return if the player receives a strike
         */
        public boolean tick(TronLocation location) {
            if (!location.atLeastDist(loc, MIN_DISTANCE)) {
                strikes++;
                loc = location;
                return true;
            }
            else {
                strikes = 0;
                loc = location;
                return false;
            }
        }

        public boolean struckOut() {
            return (strikes > STRIKES);
        }

        public int getStrikes() {
            return strikes;
        }

    }

    private RaceTimer raceTimer;

    private Map<String, AFK> afkMap = new HashMap<>();

    public AFKKiller(PlayerTracker playerTracker, RaceTimer rt) {
        Parser.getInstance().reflectListeners(this);
        this.raceTimer = rt;
    }

    // PLAYER_GRIDPOS [alekzander@forums, -112.423, -2.79337, -0.707107, 0.707107, |ek]

    @Override
    public void PLAYER_GRIDPOS(String player, float xPos, float yPos, float xDir, float yDir, String display) {
        if (raceTimer.getGameTime() < 1)
            return;

        TronLocation newLoc = new TronLocation(xPos, yPos);

        AFK afkPlayer = afkMap.get(player);

        boolean receivedTick = afkPlayer.tick(newLoc);
        boolean hasStruck = afkPlayer.struckOut();

        if(receivedTick) {
            Commands.PLAYER_MESSAGE(player, "Move faster! Strike " + afkPlayer.getStrikes() );
        }

        if(hasStruck) {
            Commands.KILL(player);
            Commands.CONSOLE_MESSAGE(player + " was killed for not racing.");
            afkMap.remove(player);
        }
    }

    @Override
    public void ROUND_COMMENCING() {
        afkMap.clear();
    }

    @Override
    public void CYCLE_CREATED(String playerId, float xPosition, float yPosition, float xDir, float yDir) {
        afkMap.put(playerId, new AFK(playerId, new TronLocation(xPosition, yPosition)));
    }

}
