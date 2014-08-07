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

package com.tronner.parser;

/**
 * Tronner - ServerEventListener
 *
 * @author TJohnW
 */
public abstract class ServerEventListener {

    /**
     * Called on every second as a game tick
     *
     * @param time the current time in seconds the round has been going on.
     */
    public void GAME_TIME(int time) {
    }

    /**
     * Called on Creation of cycles at the beginning of round.
     *
     * @param playerId   the accurate representation of a player, usually based on authorities.
     * @param xPosition  the x position of the player
     * @param yPosition  the y position of the player
     * @param xDirection the x direction of the player
     * @param yDirection the y direction of the player
     */
    public void CYCLE_CREATED(String playerId, float xPosition, float yPosition, int xDirection, int yDirection) {
    }

    /**
     * Called when a player rename s
     *
     * @param oldName the old name
     * @param newName the new name
     */
    //public void PLAYER_RENAMED(String oldName, String newName) {
    //}

    /**
     * Called on an Invalid (Custom Command).
     *
     * @param args the arguments
     */
    public void INVALID_COMMAND(String... args) {
    }

    public void ROUND_COMMENCING() {
    }

    public void TARGETZONE_PLAYER_ENTER(int globalID, float zoneX, float zoneY,
                                        String playerId, float playerX, float playerY, float playerXDir,
                                        float playerYDir, float time) {
    }
}
