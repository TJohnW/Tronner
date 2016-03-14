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

/**
 * Tronner - RaceTimer
 *
 * @author Tristan on 8/7/2014.
 */
public class RaceTimer extends ServerEventListener {

    private int gameTime = -1;

    private int timeLeft = 60;

    private PlayerTracker playerTracker;
    private boolean roundOver = false;

    public RaceTimer(PlayerTracker pm) {
        Parser.getInstance().reflectListeners(this);
        playerTracker = pm;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    @Override
    public void ROUND_COMMENCING() {
        timeLeft = 60;
        roundOver = false;
    }

    @Override
    public void GAME_TIME(int time) {
        gameTime = time;
        if(roundOver)
            return;

        if((playerTracker.playersFinished() > 0 || playerTracker.playersRacing() < 2) && playerTracker.playersRacing() > 0 && playerTracker.playersStarted() > 1) {
            timeLeft--;
            Commands.CENTER_MESSAGE(timeLeft + "                 ");
        }

        if(timeLeft <= 0 || playerTracker.playersRacing() < 1) {
            if(playerTracker.playersFinished() > 0)
                playerTracker.declareWinner();
            else
                playerTracker.endRound();
            roundOver = true;
        }

    }

    /**
     * Gets the game time
     * @return the game time
     */
    public int getGameTime() {
        return gameTime;
    }
}
