package org.example;

public class Main {
    public static void main(String[] args) {
        Checkerboard sz = new Checkerboard(5);
        sz.Zero();
        System.out.println(sz.Display());
    }
}