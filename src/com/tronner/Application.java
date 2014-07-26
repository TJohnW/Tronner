package com.tronner;

import com.tronner.parser.Parser;
import com.tronner.racing.Racing;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        new Racing().registerListeners();

        Parser p = new Parser();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()) {
            p.parseRaw(scan.nextLine());
        }

        scan.close();
    }

}
