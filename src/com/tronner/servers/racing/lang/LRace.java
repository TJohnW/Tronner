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
    public static LString CURRENT_MAP = new LString(
            "[author]: [player]"
    );
    static {
        CURRENT_MAP.c("[author]", LColors.MAROON, LColors.WHITE);
        CURRENT_MAP.c("[player]", LColors.MAROON);
    }

    /**
     * Improved faster but same rank
     */
    public static LString PLAYER_FINISHED_FASTER_REMAINS = new LString(
            "> [player] finished in [time] seconds ([change]s faster) and remains at rank [rank]."
    );
    static {

    }

    /**
     * Slower
     */
    public static LString PLAYER_FINISHED_SLOWER = new LString(
            "> [player] finished in [time] seconds ([change]s slower) and remains at rank [rank]."
    );

    /**
     * Unranked!
     */
    public static LString PLAYER_FINISHED_FASTER_TOOK = new LString(
            "[player] finished in [time] seconds and [improved] rank [rank]."
    );

    public static LString PLAYER_FINISHED_URANKED = new LString(
            "[player] finished in [time] seconds and [improved] rank [rank]."
    );

    /*
    {$ar->c->r->a}> {$ar->c->r->b}[player] {$ar->c->r->a}finished in {$ar->c->r->c}[time] {$ar->c->r->a}seconds ({$ar->c->r->d}[change]s {$ar->c->r->e}slower{$ar->c->r->a}) and remains at rank {$ar->c->r->d}[rank]{$ar->c->r->a}."; // make sure to put { } around the variables :D
    $ar->o->race_fin_fast_nr  = "{$ar->c->r->a}> {$ar->c->r->b}[player] {$ar->c->r->a}finished in {$ar->c->r->c}[time] {$ar->c->r->a}seconds ({$ar->c->r->d}[change]s {$ar->c->r->f}faster{$ar->c->r->a}) and remains at rank {$ar->c->r->d}[rank]{$ar->c->r->a}.";
    $ar->o->race_fin_fast_ur  = "{$ar->c->r->a}> {$ar->c->r->b}[player] {$ar->c->r->a}finished in {$ar->c->r->c}[time] {$ar->c->r->a}seconds ({$ar->c->r->d}[change]s {$ar->c->r->f}faster{$ar->c->r->a}) and rose to rank {$ar->c->r->d}[rank]{$ar->c->r->a} ({$ar->c->r->d}previously ranked [oldRank]{$ar->c->r->a}).";
    $ar->o->race_fin_fast_tr  = "{$ar->c->r->a}> {$ar->c->r->b}[player] {$ar->c->r->a}finished in {$ar->c->r->c}[time] {$ar->c->r->a}seconds ({$ar->c->r->d}Unranked{$ar->c->r->a}) and took rank {$ar->c->r->d}[rank]{$ar->c->r->a}.";
    */
}
