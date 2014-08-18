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

package com.tronner.servers.racing.lang;

import com.tronner.util.LString;

/**
 * Tronner - LRace
 * Language Constants used by the map manager
 * @author Tristan
 */
public class LRace {


    /**
     * Language output string,
     * Takes arguments,
     * author and player
     */
    public static LString CURRENT_MAP = new LString("[map] by [author]                       ") {{
        c("[author]", LColors.MAROON, LColors.WHITE);
        c("[map]", LColors.WHITE, LColors.LIGHT_GREY);
    }};


    public static LString PLAYER_DATA = new LString("Your best time for [map] is [time]\\\" ranking [place] of [total].") {{
        c("Your", LColors.TYLER_MAIN);
        c("[map]", LColors.TYLER_NAME, LColors.TYLER_MAIN);
        c("[time]\\\"", LColors.TYLER_NAME, LColors.TYLER_MAIN);
        c("[place]", LColors.TYLER_NAME, LColors.TYLER_MAIN);
    }};


    public static LString PLAYER_DATA_UNRANKED = new LString("You have never finished [map]. There are [total] ranks.") {{
        c("You", LColors.TYLER_MAIN);
        c("[map]", LColors.TYLER_NAME, LColors.TYLER_MAIN);
        c("[time]\\\"", LColors.TYLER_NAME, LColors.TYLER_MAIN);
        c("[total]", LColors.TYLER_NAME, LColors.TYLER_MAIN);
    }};


    /**
     * Improved faster but same rank
     */
    public static LString PLAYER_FINISHED = new LString("+ [player] finished in [time]\\\" ([data]) and [about] rank [rank].") {{
        c("+", LColors.TYLER_MAIN);
        c("[player]", LColors.TYLER_NAME, LColors.TYLER_MAIN);
        c("[time]\\\"", LColors.TYLER_NAME, LColors.TYLER_MAIN);
        c("[rank]", LColors.TYLER_NAME, LColors.TYLER_MAIN);
    }};


    public static LString TIME_DATA_FASTER = new LString("-[time]") {{
        c("-[time]", LColors.GREEN, LColors.TYLER_MAIN);
    }};


    public static LString TIME_DATA_SLOWER = new LString("+[time]") {{
        c("+[time]", LColors.RED,  LColors.TYLER_MAIN);
    }};


    public static LString TIME_DATA_UNRANKED = new LString("Unranked") {{
        c("Unranked", LColors.WHITE,  LColors.TYLER_MAIN);
    }};

    public static LString MAP_DATA_TOP = new LString("Top times for [map]:") {{
        c("Top", "0xb4cec1");
        c("[map]", "0xdc4b50", LColors.TYLER_MAIN);
    }};

    public static LString MAP_DATA_TOP_TIME = new LString("[place]) [player]\\\" | [time]") {{
        c("[place])", LColors.WHITE, "0x79a0a7");
        c("|", "0x808080", "0x79a0a7");
    }};

    public static LString RECORD_FIRST = new LString(
            "0xff3333*0xffff33*0x33ff33*0x3366ff*0xffd700Congratulate 0xffffff[player] 0xffd700for setting a new record on 0xffddaa[map] 0xffd700as the fastest time ever!0xff3333*0xffff33*0x33ff33*0x3366ff*"
    );

    public static LString RECORD_SECOND = new LString(
            "0xff3333*0xffff33*0x33ff33*0x3366ff*0xffffff[player] 0xc0c0c0just ranked as the 0xffffff2nd 0xc0c0c0fastest time for [map]!0xff3333*0xffff33*0x33ff33*0x3366ff*"
    );

    public static LString RECORD_THIRD = new LString(
            "0xff3333*0xffff33*0x33ff33*0x3366ff*0xffffff[player] 0xcd7f32just ranked as the 0xffcc003rd 0xcd7f32fastest time for [map]!0xff3333*0xffff33*0x33ff33*0x3366ff*"
    );

}
