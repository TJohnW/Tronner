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

package com.tronner;

import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.util.JsonManager;

import java.io.IOException;
import java.util.Scanner;

/**
 * Tronner - Application
 *
 * @author TJohnW
 */
public class Application {

    /**
     * This needs to be the path to the folder with
     * Tronner.jar in it.
     * MAKE SURE TO READ THIS.
     */
    public static final String PATH = "../servers/legacy/scripts/";
    //public static final String PATH= "";
    static {
        JsonManager.PATH = PATH;
    }

    /**
     * The main configuration data
     */
    public Configuration config;

    /**
     * The path to the configuration file
     */
    private String configurationFile;

    /**
     * The main parser
     */
    private Parser parser;

    /**
     * Scanner for input
     */
    private Scanner scan = new Scanner(System.in);

    /**
     * Creates a new Application to run for a server
     * @param args the command line arguments
     *             Typically just the name of the configuration file.
     */
    public Application(String... args) {
        if (initialize(args)) {
            run();
        } else {
            System.out.println("Problem initializing, please check configuration.");
        }
    }

    /**
     * Initializes the server from the configuration file
     * @return true on success, false on failure
     */
    public boolean initialize(String... args) {
        try {
            configurationFile = args[0];
            readConfig();
            String clazz = getClass().getPackage().getName() + ".servers." + config.server.toLowerCase() + "." + config.server;
            Class serverPlugin = Class.forName(clazz);
            ServerEventListener sel = (ServerEventListener) serverPlugin.newInstance();
            parser = Parser.getInstance(); // add config for reflect on load
            return true;
        } catch(IndexOutOfBoundsException e) {
            //e.printStackTrace();
            return initialize("config.JSON");
        } catch (IOException e) {
            System.out.println("Error reading config file.");
            return false;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("Could not find the specified class.. Make sure you specify it in the configuration file.");
        }

        return false;
    }

    /**
     * Reads in the configuration file into the application
     * @throws IOException
     */
    public void readConfig() throws IOException {
        config = JsonManager.loadFromJson(configurationFile, Configuration.class);
    }

    /**
     * Writes the current state of the configuration into the
     * configuration file
     * @throws IOException
     */
    public void saveConfig() throws IOException {
        JsonManager.saveAsJson(configurationFile, config, true);
    }

    /**
     * Runs the main scanning for the parser
     */
    private void run() {
        while(scan.hasNextLine()) {
            parser.parseRaw(scan.nextLine());
        }
        scan.close();
    }

    /**
     * Sleeps the application for a little :D
     * Might replace this with having the plugin run as a thread.. dont know yet.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main
     * @param args command line args
     */
    public static void main(String[] args) {
        new Application(args);
    }

}
