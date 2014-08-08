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

package com.tronner.parser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Tronner - Parser
 *
 * @author TJohnW
 */
public class Parser {

    /**
     * The singleton instance of the Parser.
     */
    private static Parser instance = null;

    /**
     * The class from which commands are reflected and loaded into.
     * In case we need support for more than one server later.
     */
    public static Class commandClazz = null;

    /**
     * Here to populate the events Map
     * Uses some Reflection but its okay.
     */
    private Map<String, ServerEvent> events = new HashMap<>();

    /**
     * Reflects the events onto their methods
     */
    private Parser(ServerEventListener sel, Class commandClazz, boolean reflectListeners) {
        Parser.commandClazz = commandClazz;
        reflectEvents();
        if(reflectListeners)
            reflectListeners(sel);
    }

    private Parser(ServerEventListener sel, Class commandClazz) {
        this(sel, commandClazz, true);
    }

    /**
     * Gets the only instance of Parser or creates an instance.
     * @return the Parser object. (singleton)
     */
    public static Parser getInstance(ServerEventListener sel, boolean reflectListeners) {
        return getInstance(sel, ServerEventListener.class, reflectListeners);
    }

    public static Parser getInstance(ServerEventListener sel) {
        return getInstance(sel, ServerEventListener.class, true);
    }

    public static Parser getInstance(ServerEventListener sel, Class commandClazz, boolean reflectListeners) {
        if(instance == null) instance = new Parser(sel, commandClazz, reflectListeners);
        return instance;
    }

    public static Parser getInstance(ServerEventListener sel, Class commandClazz) {
        if(instance == null) instance = new Parser(sel, commandClazz);
        return instance;
    }

    public ServerEvent getEvent(String event) {
        return events.get(event);
    }

    public void setEvent(String name, ServerEvent se) {
        events.put(name, se);
    }

    /**
     * Called to parse a command and a given array of arguments.
     * @param command the command name
     * @param args the arguments to pass
     */
    public void parse(String command, String... args) {
        ServerEvent se = events.get(command);
        if(se != null) {
            se.onEvent(args);
            System.out.println("# Input Handled: " + command);
        }
        else {
            System.out.println("Ignored input: " + command + " " + Arrays.toString(args));
        }
    }

    /**
     * Parses a raw string from the game into a more understandable form
     * @param input the data input
     */
    public void parseRaw(String input) {
        String[] bits = input.split("\\s+");
        parse(bits[0], Arrays.copyOfRange(bits, 1, bits.length));
    }

    /**
     * Reflects the Events of the specified command class into the map
     */
    private void reflectEvents() {
        for(Method m: commandClazz.getDeclaredMethods()) {
            String clazzToLoad = commandClazz.getPackage().getName() + ".events." + m.getName();
            try {
                setEvent(m.getName(), (ServerEvent) Class.forName(clazzToLoad).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                System.out.println("Problem instantiating or finding a class for server event: " + m.getName());
            }
        }
    }

    /**
     * Uses the ServerEventListener class and uses Reflection to
     * attach the listeners neccessary to update the object about
     * events received.
     * @param sel the ServerEventListener to add as a listener
     */
    public void reflectListeners(ServerEventListener sel) {
        Class clazz = sel.getClass();
        for(Method m: clazz.getDeclaredMethods()) {
            ServerEvent se = getEvent(m.getName());
            if(se == null)
                continue;
            se.addListener(sel);
        }
    }

}
