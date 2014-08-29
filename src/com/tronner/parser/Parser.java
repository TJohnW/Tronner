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

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.InvocationTargetException;
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
     * Creates a parser without reflecting any events.
     * @param commandClazz the command class
     */
    private Parser(Class commandClazz) {
        Parser.commandClazz = commandClazz;
        reflectEvents();
        System.out.println(events.size() + " events reflected.");
    }

    /**
     * Creates a parser and reflects the listeners onto the given
     * sel
     * @param sel the sel to reflect onto
     * @param commandClazz the command class
     */
    private Parser(ServerEventListener sel, Class commandClazz) {
        this(commandClazz);
        reflectListeners(sel);
    }


    /**
     * Gets the only instance of Parser or creates an instance.
     * @return the Parser object. (singleton)
     */
    public static Parser getInstance(ServerEventListener sel, Class commandClazz) {
        if(instance == null) instance = new Parser(sel, commandClazz);
        return instance;
    }

    /**
     * Creates a parser without registering any listeners
     * @param commandClazz the command class
     * @return the new Parser instance
     */
    public static Parser getInstance(Class commandClazz) {
        if(instance == null) instance = new Parser(commandClazz);
        return instance;
    }

    /**
     * Careful, could return null
     * @return the parser
     */
    public static Parser getInstance() {
        return instance;
    }

    /**
     * Gets an event from the map of events
     * @param event the event name
     * @return the ServerEvent
     */
    public ServerEvent getEvent(String event) {
        return events.get(event);
    }

    /**
     * Sets an Event in the map
     * @param name the name of the event
     * @param se the ServerEvent to set
     */
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
            //System.out.println("# Input Handled: " + command);
        }
        else {
            //System.out.println("Ignored input: " + command + " " + Arrays.toString(args));
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
    /*
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
    */



    /**
     * Reflects the Events of the specified command class into the map
     * Newer experimental method of adding events.
     * Is slightly slower but worth it imo.
     * Reflects events using primitive and primitive wrapper
     * types to the methods.
     */
    private void reflectEvents() {

        for(final Method m: commandClazz.getDeclaredMethods()) {

            final Class<?>[] types = m.getParameterTypes();
            setEvent(m.getName(), new ServerEvent() {

                @Override
                public void onEvent(ServerEventListener il, String... args) {

                    Object[] params = new Object[types.length];

                    /* Now lets parse all of the params into their expected types */
                    /* Lets start with fixing the primitive types */

                    for (int i = 0; i < types.length; i++) {

                        Class<?> type = types[i];
                        if (type.isPrimitive())
                            type = ClassUtils.primitiveToWrapper(type);

                        /* If its a string, leave it be */
                        if(type.getSimpleName().equals("String")) {
                            params[i] = args[i];
                            continue;
                        }

                        if (type.isArray() && i == types.length - 1)
                            params[i] = Arrays.copyOfRange(args, i, args.length, String[].class);

                        else {
                            try {
                                params[i] = type.getMethod("valueOf", String.class).invoke(null, args[i]);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                                System.out.println("There was an error parsing the value of an event.");
                            }
                        }
                    }

                    try {
                        m.invoke(il, params);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        System.out.println("There was an error invoking the method using reflection.");
                    }
                }

            });

        }

    }


    /**
     * Uses the ServerEventListener class and uses Reflection to
     * attach the listeners necessary to update the object about
     * events received.
     * @param sel the ServerEventListener to add as a listener
     */
    public void reflectListeners(ServerEventListener sel) {
        Class clazz = sel.getClass();
        for(Method m: clazz.getDeclaredMethods()) {
            ServerEvent se = getEvent(m.getName());
            if(se != null)
                se.addListener(sel);
        }
    }

}
