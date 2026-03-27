package org.example;

public class Main {
    public static void main(String[] args) {
        Checkerboard sz = new Checkerboard(5);
        sz.Zero();
        sz.placeK(0,1);
        sz.placeK(0,2);
        sz.placeStar(0,3);
        sz.calcAttack();
        System.out.println(sz.Display());

    }
}