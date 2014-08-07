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

package com.tronner.servers.racing.maps;

import java.util.regex.Pattern;

/**
 * Tronner - RacingMap
 *
 * @author TJohnW
 */
public class RacingMap {

    private String path;

    /**
     * Loaded in by parsing the path
     */
    private transient int axes;

    /**
     * Loaded in by parsing the path
     */
    private transient String author;

    /**
     * Loaded in by parsing the path
     */
    private transient String name;

    /**
     * Creates an Object to represent a RacingMap
     * parses the path to find the axes, author, and name
     * @param path the path to the map file.
     */
    public RacingMap(String path) {
        this.path = path;

        String[] bits = path.split(Pattern.quote("/"));
        author = bits[0];

        String mapInfo = bits[2];

        String[] mapData = mapInfo.split(Pattern.quote("-"));
        name = mapData[0];

        String[] mapAxesData = mapData[1].split(Pattern.quote("."));
        axes = Integer.parseInt(mapAxesData[1]);
    }

    /**
     * Gets the author of this map
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the axes of this map
     * @return the axes
     */
    public int getAxes() {
        return axes;
    }

    /**
     * Gets the maps path
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the maps name
     * @return the name
     */
    public String getName() {
        return name;
    }
}
