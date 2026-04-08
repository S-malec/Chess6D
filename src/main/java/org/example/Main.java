package org.example;

public class Main {
    public static void main(String[] args) {
        Checkerboard sz = new Checkerboard(5);

        // sz.placeK(0,1);
        // sz.placeK(0,2);
        // sz.placeStar(0,3);
        // sz.calcAttack();
        sz.placeK(4, 0);
        // sz.placeCannon(3, 2, '|');
        sz.calcAttack();
        System.out.println(sz.display());

    }
}