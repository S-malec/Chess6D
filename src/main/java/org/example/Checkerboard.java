package org.example;

import java.util.ArrayList;

public class Checkerboard {
    public char[][] szachownica;
    private int N;
    private ArrayList<Pionek> pionki = new ArrayList<>();

    public Checkerboard(int N) {
        this.N = N;
        szachownica = new char[N][N];
        Zero();
    }

    public void Zero() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                szachownica[i][j] = '0';
            }
        }
    }

    public String Display() {
        StringBuilder sz = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sz.append(szachownica[i][j]);
                sz.append(" ");
            }
            sz.append( "\n" );
        }
        return sz.toString();
    }

    public void placeK(int i, int j) {
        szachownica[i][j] = 'K';
        Pionek p = new Pionek(i, j);
        pionki.add(p);
    }

    public void calcRange() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

            }
        }
    }


}
