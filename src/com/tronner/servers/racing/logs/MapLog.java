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


import java.util.*;

/**
 * Tronner - MapLog
 *
 * @author TJohnW
 */
public class MapLog {

    /**
     * Our comparator for PlayerTimes.
     */
    public static Comparator<PlayerTime> comparator = new Comparator<PlayerTime>() {
        @Override
        public int compare(PlayerTime o1, PlayerTime o2) {
            return (o1.getTime() < o2.getTime()) ? -1 : 1;
        }
    };

    /**
     * The map this MapLog is for.
     */
    private String map;

    /**
     * The list of PlayerTimes on this map.
     */
    private List<PlayerTime> records = new ArrayList<>();

    /**
     * Here to speed up finding a players rank
     */
    private transient Map<String, PlayerTime> ranks = new HashMap<>();

    /**
     * This creates an empty MapLog with the given name.
     * This should only be used to create logs for maps that dont
     * have logs yet.
     * @param mapName the name of the map to create a log for.
     */
    public MapLog(String mapName) { map = mapName; }

    /**
     * Here to speed up quick access of the players ranks
     * at the start of the round and when loaded into memory
     */
    public void cache() {
        ranks = new HashMap<>();
        for(int i = 0; i < records.size(); i++) {
            PlayerTime pt = records.get(i);
            pt.setRank(i+1);
            ranks.put(pt.getPlayer(), pt);
        }
    }

    /**
     * Sorts the records.
     */
    public void sort() {
        Collections.sort(records, comparator);
        cache();
    }

    /**
     * Gets the rank of a playerId
     * @param playerId the players id to find
     * @return the rank of the player on this MapLog
     */
    public int getRank(String playerId) {
        PlayerTime pt = ranks.get(playerId);
        if(pt != null)
            return pt.getRank();
        return -1;
    }

    /**
     * Updates the record for a player, and returns
     * their new rank. Will also add a new record
     * if they dont have one already
     * @param playerTime The PlayerTime object to update
     */
    public int updateRecord(PlayerTime playerTime) {
        // Fancy caching help here
        if(ranks.containsKey(playerTime.getPlayer())) {
            if(playerTime.getTime() < ranks.get(playerTime.getPlayer()).getTime()) {
                ranks.get(playerTime.getPlayer()).setTime(playerTime.getTime());
            } else {
                // no change in this players rank, lets not re sort
                return getRank(playerTime.getPlayer());
            }
        } else {
            records.add(playerTime);
        }
        sort();
        return getRank(playerTime.getPlayer());
    }

    /**
     * Called to rename a record in the this MapLog
     * @param playerId the player to rename
     * @param newPlayerId the new name
     * @return true on success
     */
    public boolean renameRecord(String playerId, String newPlayerId) {
        if(!ranks.containsKey(playerId))
            return false;
        ranks.get(playerId).setPlayer(newPlayerId);
        return true;
    }

}