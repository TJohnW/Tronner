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

package com.tronner.servers.racing.logs;

/**
 * Tronner - PlayerTime
 *
 * @author TJohnW
 */
public class PlayerTime {

    /**
     * Here to help improve caching of ranks
     */
    private transient int rank;

    /**
     * The players time
     */
    private double time;

    /**
     * The players name
     */
    private String player;

    /**
     * An object to represent a player record.
     * @param player The players name
     * @param time the players time
     */
    public PlayerTime(String player, double time) {
        this.player = player;
        this.time = time;
    }

    /**
     * Sets this rank
     * @param newRank the rank to set
     */
    public void setRank(int newRank) {
        rank = newRank;
    }

    /**
     * Gets this rank
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Updates the time of this PlayerTime
     * @param newTime the new time
     */
    public void setTime(double newTime) {
        time = newTime;
    }

    /**
     * Gets the time.
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the players name (for updating players times)
     * @param newPlayer The new name to set
     */
    public void setPlayer(String newPlayer) {
        player = newPlayer;
    }

    /**
     * Gets the players id
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Specify java equals for PlayerTime objects
     * @param pt the PlayerTime to check / object
     * @return true on "equality" based on PLAYER NAME ONLY
     */
    @Override
    public boolean equals(Object pt) {
        return (pt instanceof PlayerTime) && ((PlayerTime) pt).getPlayer().equals(player);
    }
}
