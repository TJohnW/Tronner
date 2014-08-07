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

package com.tronner.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Tronner - TronStringBuilder
 *
 * @author TJohnW
 */
public class TronStringBuilder {

    private Map<String, String> colors = new HashMap<>();

    public String baseColor = "ffffff";

    public String current = "";

    public TronStringBuilder(String baseColor) {
        this.baseColor = baseColor;
    }

    public TronStringBuilder(String baseColor, Map<String, String> colors) {
        this(baseColor);
        this.colors = colors;
    }

    public TronStringBuilder append(String str) {
        current += str;
        return this;
    }

    public TronStringBuilder append(String color, String str) {
        append(color, str, false);
        return this;
    }

    public TronStringBuilder append(String color, String str, boolean backToBase) {
        current += formatColor(color) + str + ((backToBase) ? formatColor(baseColor) : "");
        return this;
    }

    public String flush() {
        String out = current;
        current = "";
        return out;
    }

    public void addColor(String name, String color) {
        colors.put(name, color);
    }

    public void removeColor(String name) {
        colors.remove(name);
    }

    public String getColor(String name) {
        return colors.get(name);
    }

    public String formatColor(String color) {
        return "0x" + color;
    }

}
