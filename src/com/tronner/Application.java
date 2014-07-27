package com.tronner;

import com.tronner.parser.Parser;
import com.tronner.parser.ServerEventListener;
import com.tronner.servers.racing.Racing;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Parser p = Parser.getInstance(new Racing());
        p.reflectListeners(new ServerEventListener() {
            @Override
            public void GAME_TIME(int time) {
                System.out.println("AOAOAOAO");
            }
        });

        p.parseRaw("GAME_TIME 10");

        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()) {
            p.parseRaw(scan.nextLine());
        }

        scan.close();
    }

}
