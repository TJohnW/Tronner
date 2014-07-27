package com.tronner.parser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
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
    private Parser(ServerEventListener sel, Class commandClazz) {
        Parser.commandClazz = commandClazz;
        reflectEvents();
        reflectListeners(sel);
    }

    /**
     * Gets the only instance of Parser or creates an instance.
     * @return the Parser object. (singleton)
     */
    public static Parser getInstance(ServerEventListener sel) {
        return getInstance(sel, ServerEventListener.class);
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
        if(se != null) se.onEvent(args);
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
