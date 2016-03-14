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

package com.tronner.servers.racing.rankings;

import com.google.gson.reflect.TypeToken;
import com.tronner.dispatcher.Commands;
import com.tronner.servers.racing.logs.Logger;
import com.tronner.servers.racing.logs.MapLog;
import com.tronner.servers.racing.logs.PlayerTime;
import com.tronner.servers.racing.maps.MapManager;
import com.tronner.servers.racing.maps.RacingMap;
import com.tronner.servers.racing.players.PlayerTracker;
import com.tronner.util.JsonManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Tronner - RankingsManager
 *
 * @author Tristan on 8/29/2014.
 */
public class Rankings {

    private Logger logger; // This is only for reading rankings!

    private MapManager mapManager;

    private Map<String, RankedPlayer> rankedPlayerMap = new HashMap<>();
    private List<RankedPlayer> rankedPlayerList = new ArrayList<>();

    private Comparator<RankedPlayer> rankedPlayerComparator = new Comparator<RankedPlayer>() {

        @Override
        public int compare(RankedPlayer o1, RankedPlayer o2) {
            return o1.getSum() - o2.getSum();
        }
    };

    public Rankings(PlayerTracker playerTracker, MapManager mapManager) {
        logger = new Logger(playerTracker);
        this.mapManager = mapManager;
        updateAll();
    }

    /**
     * Starts a new thread to run the rankings update from.
     */
    public void threadedUpdate() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                updateAll();
            }

        }).start();

        Commands.CONSOLE_MESSAGE("Caching player rankings. Website updating.");
    }

    public void addRankedPlayer(RankedPlayer player) {
        rankedPlayerList.add(player);
        rankedPlayerMap.put(player.getName(), player);
    }

    public void updateBecauseOf(String map, String player) {
        // TO DO
    }

    /**
     * This should only be called when a total recache needs to be performed
     */
    public void updateAll() {

        int countOfRecs = 0;
        for(RacingMap rm: mapManager.getMaps().values()) {
            System.out.println("Trying to load: " + rm.getName());
            countOfRecs += logger.getLog(rm.getName()).count();
        }

        System.out.println("Total Recs: " + countOfRecs);

        // clear the data structures, lets start from scratch here

        rankedPlayerMap = new HashMap<>();
        rankedPlayerList = new ArrayList<>();

        int emptyMaps = 0; // the number to add for every player at the end, (these maps have no winners yet. but we weigh them still).
        int numMaps = mapManager.getMaps().size();

        int sumForFirstNotFound = 0;

        // allocate ranks for each map and the finished players
        for(RacingMap rm: mapManager.getMaps().values()) {
            MapLog ml = logger.getLog(rm.getName());
            if(ml == null) {
                emptyMaps++;
            } else {

                for(RankedPlayer rp: rankedPlayerList) {
                    if(ml.getRank(rp.getName()) == -1) {
                        rp.setSum(rp.getSum() + ml.count());
                    }
                }

                // we have a map log to handle now. lets add all of the ranked players we find into the list!
                for(PlayerTime pt: ml.getRecords()) {
                    if(!rankedPlayerMap.containsKey(pt.getPlayer())) {
                        RankedPlayer newPlayer = new RankedPlayer(pt.getPlayer());
                        newPlayer.setSum(sumForFirstNotFound);
                        addRankedPlayer(newPlayer);
                    }
                    RankedPlayer rp = rankedPlayerMap.get(pt.getPlayer());
                    rp.setSum(rp.getSum() + pt.getRank());
                }

                sumForFirstNotFound += ml.count();
                logger.unloadMapLog(rm.getName(), false);
            }
        }

        for(RankedPlayer rp: rankedPlayerList) {
            rp.setSum(rp.getSum() + emptyMaps);
            rp.setAverage((double) rp.getSum() / numMaps);
        }

        Collections.sort(rankedPlayerList, rankedPlayerComparator);

        int i = 1;
        for(RankedPlayer rp: rankedPlayerList)
            rp.setRank(i++);

        saveRankings();
    }

    @SuppressWarnings("unchecked")
    public void loadRankings() {
        try {
            Type listType = new TypeToken<ArrayList<RankedPlayer>>() {}.getType();
            rankedPlayerList = JsonManager.loadFromJson("data/rankings.JSON", listType);
        } catch (IOException e) {
            System.out.println("Unable to load rankings log.");
        }
    }

    public void saveRankings() {
        try {
            JsonManager.saveAsJson("data/rankings.JSON", rankedPlayerList, true);
        } catch (IOException e) {
            System.out.println("Unable to save rankings log.");
        }
    }

}
