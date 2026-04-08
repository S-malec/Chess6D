package org.example;

public class Cannon {
    int i, j;
    char c;
    public Cannon(int i, int j, char c) {
        this.i = i;
        this.j = j;
        if (c != '/' && c != '|' && c != '\\' && c != '-') {
            throw new IllegalArgumentException();
        } else this.c = c;
    }
}
