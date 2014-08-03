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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tronner - JsonManager
 *
 * @author TJohnW
 */
public class JsonManager {

    /**
     * Used to save an object as JSON to a file path.
     * Always prints ugly JSON
     * @param path the path to save, including the filetype
     * @param toJson the object to encode
     * @throws IOException
     */
    public static void saveAsJson(String path, Object toJson) throws IOException {
        saveAsJson(path, toJson, false);
    }

    /**
     * Used to save an object as JSON to a file path
     * @param path the path to save, including the filetype
     * @param toJson the object to encode
     * @param pretty print pretty json?
     * @throws IOException
     */
    public static void saveAsJson(String path, Object toJson, boolean pretty) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(new File(path)));
        Gson g;
        if(pretty) g = new GsonBuilder().setPrettyPrinting().create();
        else g = new Gson();
        output.write(g.toJson(toJson));
        output.close();
    }

    public static <T> T loadFromJson(String path, Class<T> clazz) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        Gson g = new Gson();
        return g.fromJson(new String(encoded, StandardCharsets.UTF_8), clazz);
    }

}
