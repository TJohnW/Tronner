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

    public Map<String, ServerEvent> events = new HashMap<String, ServerEvent>() {{
        try {
            Class clazz = Parser.class;
            for(Field f: clazz.getFields()) {
                String fieldName = f.getName();
                if("events".equals(fieldName)) continue;

                ServerEvent se = (ServerEvent) f.get(null);
                put(fieldName, se);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }};

    public void parse(String command, String... args) {
        ServerEvent se = events.get(command);
        if(se != null) {
            se.onEvent(args);
        } else {
            System.out.println("Unhandled input: " + command + " " + Arrays.toString(args));
        }
    }

}
