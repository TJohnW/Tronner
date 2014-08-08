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

package com.tronner.dispatcher;

/**
 * Tronner - Commands
 *
 * The commonly used Commands, will create more files for more specific
 * needs if needed.
 *
 * @author TJohnW
 */
public class Commands {

    /**
     * Writes to the server, depending on the configuration.
     * Could be cmd.txt but usually is just STDOUT.
     * @param output The string to write.
     */
    public static void out(String output) {
        System.out.println(output); // for now.
    }

    /**
     * Writes a commend to the Plugin log file.
     * @param commentInformation The string to comment.
     */
    public static void comment(String commentInformation) {

    }

    public static void MAP_FILE(String mapPath) {
        out("MAP_FILE " + mapPath);
    }

    public static void CONSOLE_MESSAGE(String message) {
        out("CONSOLE_MESSAGE " + message);
    }

    public static void CENTER_MESSAGE(String message) {
        out("CENTER_MESSAGE " + message);
    }

    public static void ROUND_CENTER_MESSAGE(String message) {
        out("ROUND_CENTER_MESSAGE" + message);
    }

    public static void KILL(String playerName) {
        out("KILL " + playerName);
    }

    public static void ADMIN_KILL_MESSAGE(boolean on) {
        out("ADMIN_KILL_MESSAGE " + ((on) ? "1" : "0"));
    }

    public static void PLAYER_MESSAGE(String receiver, String message) {
        out("PLAYER_MESSAGE " + receiver + " " + message);
    }

    public static void DECLARE_ROUND_WINNER(String player) {
        out("DECLARE_ROUND_WINNER " + player);
    }

    public static void CYCLE_RUBBER(int i) {
        out("CYCLE_RUBBER "+ i);
    }
}
