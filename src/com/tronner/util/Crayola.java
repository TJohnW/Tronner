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

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Tronner - Crayola
 *
 * @author TJohnW
 */
public class Crayola {

    /**
     * Tronner - CrayolaColor
     *
     * @author TJohnW
     */
    public static class CrayolaColor {

        private String name;
        private String hex;
        private String rgb;

        public CrayolaColor(String name, String hex, String rgb) {
            this.name = name;
            this.hex = hex;
            this.rgb = rgb;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setHex(String hex) {
            this.hex = hex;
        }

        public String getHex() {
            return hex;
        }

        public void setRgb(String rgb) {
            this.rgb = rgb;
        }

        public String getRgb() {
            return rgb;
        }

    }

    public static String colorList = "";

    public static List<CrayolaColor> colors = new ArrayList<>();
    static {
        Type listType = new TypeToken<ArrayList<CrayolaColor>>() {}.getType();
        try {
            colors = JsonManager.loadFromJson("data/crayola.JSON", listType);
            // Make hex codes tron friendly
            for(CrayolaColor c: colors) {
                c.setHex(c.getHex().replace("#", "0x").toLowerCase());
                colorList += c.getHex() + c.getName() + ": " + c.getHex().replace("0x", "") + "\\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
