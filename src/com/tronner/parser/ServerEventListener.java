package com.tronner.parser;

import java.awt.event.MouseAdapter;
import java.net.InetAddress;

/**
 *
 * @author TJohnW
 */
public abstract class ServerEventListener {

    public abstract void registerListeners();

    /**
     * Called on every second as a game tick
     * @param time the current time in seconds the round has been going on.
     */
    public void GAME_TIME(int time) {}

    /**
     * Called on Creation of cycles at the beginning of round.
     * @param playerId the accurate representation of a player, usually based on authorities.
     * @param xPosition the x position of the player
     * @param yPosition the y position of the player
     * @param xDirection the x direction of the player
     * @param yDirection the y direction of the player
     */
    public void CYCLE_CREATED(String playerId, float xPosition, float yPosition, int xDirection, int yDirection) {}

    /**
     * Called when a player rename s
     * @param oldName the old name
     * @param newName the new name
     */
    public void PLAYER_RENAMED(String oldName, String newName) {}



}
