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

import com.google.gson.reflect.TypeToken;
import com.tronner.util.JsonManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Tronner - ExperienceManager
 *
 * @author Tristan on 8/7/2014.
 */
public class ExperienceManager {

    public static Comparator<PlayerXP> playerXPComparator = new Comparator<PlayerXP>() {

        @Override
        public int compare(PlayerXP o1, PlayerXP o2) {
            return o1.getXp() - o2.getXp();
        }
    };

    private Integer[] levelToXp = new Integer[] { 0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470,
            5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815,
            27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945,
            123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599, 407015,
            449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421, 1336443, 1475581,
            1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295,
            5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431 };

    private List<PlayerXP> players = new ArrayList<>();

    private Map<String, PlayerXP> playerMap = new HashMap<>();

    public ExperienceManager() {
        loadXPLog();
        cache();
    }

    @SuppressWarnings("unchecked")
    public void loadXPLog() {
        try {
            Type listType = new TypeToken<ArrayList<PlayerXP>>() {}.getType();
            players = JsonManager.loadFromJson("data/xp.JSON", listType);
        } catch (IOException e) {
            System.out.println("Unable to load xp log.");
        }
    }

    public void saveXPLog() {
        try {
            JsonManager.saveAsJson("data/xp.JSON", players, true);
        } catch (IOException e) {
            System.out.println("Unable to save xp log.");
        }
    }

    /**
     * Sorts the xp
     */
    public void sort() {
        Collections.sort(players, playerXPComparator);
        System.out.println("Number of players now sorted by XP: " + players.size());
        cache();
        saveXPLog();
    }

    public void cache() {
        for(PlayerXP pxp: players) {
            playerMap.put(pxp.getName(), pxp);
        }
    }

    public void addXP(String playerName, int xp) {
        playerMap.get(playerName).addXp(xp);
        sort();
    }

    public void removeXP(String playerName, int xp) {
        playerMap.get(playerName).removeXp(xp);
        sort();
    }

    public int getLevel(String playerName) {
        if(!playerMap.containsKey(playerName)) return 0;
        int level = 0;
        int xp = playerMap.get(playerName).getXp();
        for(int i = 0; i < levelToXp.length; i++) {
            if(levelToXp[i] <= xp)
                level = i;
        }
        return level;
    }

    public void renamePlayer(String playerName, String newName) {
        PlayerXP p = playerMap.get(playerName);
        p.setName(newName);
        playerMap.remove(playerName);
        playerMap.put(newName, p);
        saveXPLog();
    }

    public static void main(String[] args) throws IOException {
        ExperienceManager em = new ExperienceManager();
    }

}
