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

/**
 * Tronner - LColors
 *
 * @author Tristan on 8/6/2014.
 */
public class LColors {

    public static final String LIGHT_GREY = "0xe5e5e5"; //Typically used for Map by://

    public static final String MAROON = "0x883333"; //typically used for author's name//

    public static final String WHITE = "0xffffff"; //white//

    public static final String OFF_WHITE = "0xeaeaea";

    public static final String LIGHT_ORANGE = "0xffddaa";



    public static final String ERROR_RED = "0xff7777"; //generic error and warning red.//



    public static final String DARK_RED = "0xcc4444";

    public static final String DARK_GREY = "0x888888";

    public static final String GREY = "0xaaaaaa";

    public static final String UGLY_YELLOW = "0xffff00";

    /**
     * Colors for xp, levelling system
     */

    public static final String LEVEL_BLUE = "0x4c79bc"; //the start of level messages, $//

    public static final String GREY_BLUE = "0xa2b9dc"; //typically used for player's name in level messages, and their xp number//

    public static final String LEVEL_RED = "0xbc574c"; // typically used for the level and xp given//

    /**
     * Colors for important 1st 2nd 3rd placed messages!
     */

    public static final String STAR_RED = "0xff3333"; //STARS * ARE USED IN THE MESSAGES AT THE START AND END//
    public static final String STAR_YELLOW = "0xffff33"; //STARS *  ARE USED IN THE MESSAGES AT THE START AND END//
    public static final String STAR_GREEN = "0x33ff33"; //STARS * ARE USED IN THE MESSAGES AT THE START AND END//
    public static final String STAR_BLUE = "0x3366ff"; //STARS * ARE USED IN THE MESSAGES AT THE START AND END//

    public static final String FIRST_PLACE_GOLD = "0xffd700"; //for other text in sentence

    public static final String SECOND_PLACE_SILVER = "0xc0c0c0"; //for other text in sentence

    public static final String THIRD_PLACE_BRONZE = "0xcd7f32"; //for other text in sentence

    // 1st place message doesn't use a colored number 1. 2nd place message uses white for the colored number 2.

    public static final String THIRD_PLACE_NUMBER_COLOR = " 0xffcc00"; //for the "3rd", matches the bronze color nicely

    public static final String TOP_THREE_TIMES_GREY = "0xb4cec1"; // every round top 3 time list.
    public static final String TOP_THREE_TIMES_ORANGE = "0xff7711"; // MAP NAME IN THIS COLOR
    public static final String TOP_THREE_TIMES_DARKGREY = "0x547980"; //List of top three players at the start of round!


    /**
     * Colors for queue
     */

    public static final String RED_ORANGE = "0xea5639"; //the + start of the queue messages//

    public static final String MILKY_BLUE = "0x94b3f4"; //edited the queue or queue numbers left//

    public static final String DARK_ORANGE = "0xe65438"; //disabled queue//

    public static final String START_GREEN = "0x75be62"; //enabled queue, map name when queued//

    /**
     * Colors for timer, times and AFKers
     */
    public static final String LOGGED_OUT_YELLOW = "0xede777"; //warning for logged out racers

    public static final String GREEN = "0x77ff77"; //faster time color//

    public static final String RED = "0xff7f7f"; //slower time color//

    public static final String AFK_RED = "0xef4f3e"; //AFK killed message is in this color//

    public static final String LIGHT_BLUE = "0x99aaff"; // Map name in time/rank message


}
