package com.tronner.racing;

import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;

/**
 * Tronner
 *
 * @author TJohnW
 */
public class Racing extends ServerEventListener {

    @Override
    public void registerListeners() {
        Parser.GAME_TIME.addListener(this);
        Parser.CYCLE_CREATED.addListener(this);
        Parser.PLAYER_RENAMED.addListener(this);
    }

    @Override
    public void GAME_TIME(int time) {
        System.out.println("Time!!!");
    }

    @Override
    public void CYCLE_CREATED(String playerName, float xPosition, float yPosition, int xDirection, int yDirection) {
        System.out.println("A cycle was created at x= " + xPosition + " y= " + yPosition);
    }

    @Override
    public void PLAYER_RENAMED(String oldName, String newName) {
        System.out.println("Someone renamed!");
    }

}
