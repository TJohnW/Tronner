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

    public void GAME_TIME(int time) {
    }

    public void CYCLE_CREATED(String playerId, float xPosition, float yPosition, float xDir, float yDir) {
    }

    public void PLAYER_RENAMED(String oldName, String newName, String ip, String displayName) {
    }

    public void PLAYER_ENTERED(String name, String ip, String displayName) {
    }

    public void PLAYER_LEFT(String player, String ip) {
    }

    public void INVALID_COMMAND(String... args) {
    }

    public void ROUND_COMMENCING() {
    }

    public void TARGETZONE_PLAYER_ENTER(int globalID, float zoneX, float zoneY,
                                        String playerId, float playerX, float playerY, float playerXDir,
                                        float playerYDir, float time) {
    }

    public void DEATH_SUICIDE(String player) {
    }

    public void DEATH_FRAG(String playerKilled, String killer) {
    }

    public void DEATH_DEATHZONE(String player) {
    }

    public void DEATH_RUBBERZONE(String player) {
    }

    public void PLAYER_KILLED(String player, String ip, float x, float y, float xDir, float yDir) {
    }

    public void ONLINE_PLAYER(String arg) {
    }

    /// PLAYER_GRIDPOS [alekzander@forums, -112.423, -2.79337, -0.707107, 0.707107, |ek]
    public void PLAYER_GRIDPOS(String player, float xPos, float yPos, float xDir, float yDir, String display) {
    }
}
