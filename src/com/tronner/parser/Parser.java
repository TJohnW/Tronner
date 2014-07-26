package com.tronner.parser;

import com.tronner.parser.events.CYCLE_CREATED;
import com.tronner.parser.events.GAME_TIME;
import com.tronner.parser.events.PLAYER_RENAMED;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TJohnW
 */
public class Parser {

    public static ServerEvent GAME_TIME                 = new GAME_TIME();
    public static ServerEvent CYCLE_CREATED             = new CYCLE_CREATED();
    public static ServerEvent PLAYER_RENAMED            = new PLAYER_RENAMED();

    /**
     * Here to populate the events Map
     * Uses some Reflection but its okay.
     */
    public Map<String, ServerEvent> events = new HashMap<String, ServerEvent>() {{
        try {
            Class clazz = Parser.class;
            for(Field f: clazz.getFields()) {
                String fieldName = f.getName();
                if("events".equals(fieldName)) continue;
                put(fieldName, (ServerEvent) f.get(null));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }};

    /**
     * Called to parse a command and a given array of arguments.
     * @param command the command name
     * @param args the arguments to pass
     */
    public void parse(String command, String... args) {
        ServerEvent se = events.get(command);
        if(se != null) {
            se.onEvent(args);
        } else {
            System.out.println("Unhandled input: " + command + " " + Arrays.toString(args));
        }
    }

    /**
     * Parses a raw string from the game into a more understandable form
     * @param input the data input
     */
    public void parseRaw(String input) {
        String[] bits = input.split("\\s+");
        this.parse(bits[0], Arrays.copyOfRange(bits, 1, bits.length));
    }

}
