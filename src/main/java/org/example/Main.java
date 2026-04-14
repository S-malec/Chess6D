package org.example;

public class Main {
    public static void main(String[] args) {
        Checkerboard sz = new Checkerboard(5);

        sz.placeK(6, 1);
        sz.placeCannon(5, 2, '/');
        sz.placeStar(4, 3);
        sz.calcAttack();

        sz.calcAttack();
        System.out.println(sz.display());

    }
}