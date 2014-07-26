package com.tronner;

import com.tronner.parser.Parser;
import com.tronner.racing.Racing;

import java.util.Arrays;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        new Racing().registerListeners();

        Parser p = new Parser();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()) {
            String[] bits = scan.nextLine().split("\\s+");
            p.parse(bits[0], Arrays.copyOfRange(bits, 1, bits.length));
        }

        scan.close();
    }

}
