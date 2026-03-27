package org.example;

import java.util.ArrayList;

public class Checkerboard {
    public char[][] szachownica;
    private int N;
    private ArrayList<Pionek> pionki = new ArrayList<>();

    public Checkerboard(int N) {
        this.N = N;
        szachownica = new char[N+2][N+2];
        Zero();
    }

    public void Edges() {
        N+=2; //DO DOKONCZENIA NIE DZIALA ZROBIC
        szachownica[0][0] = '.';
        szachownica[0][N] = '.';
        szachownica[N][0] = '.';
        szachownica[N][N] = '.';
    }

    public void Zero() {
        N+=2;
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
        i+=1;
        j+=1;
        szachownica[i][j] = 'K';
        Pionek p = new Pionek(i, j);
        pionki.add(p);
    }

    public void placeStar(int i, int j) {
        i+=1;
        j+=1;
        szachownica[i][j] = '*';
    }

    public void attack(Pionek p) {
        updateCell(p.i, p.j - 1);     // lewo
        updateCell(p.i, p.j + 1);     // prawo
        updateCell(p.i - 1, p.j);     // gora
        updateCell(p.i + 1, p.j);     // dol

        updateCell(p.i + 1, p.j + 1); // skos dol-prawo
        updateCell(p.i - 1, p.j - 1); // skos gora-lewo
        updateCell(p.i - 1, p.j + 1); // skos gora-prawo
        updateCell(p.i + 1, p.j - 1); // skos dol-lewo
    }

    private void updateCell(int row, int col) {
        if (row >= 0 && row < szachownica.length && col >= 0 && col < szachownica[0].length) {

            if (!isObstacle(row, col)) {
                szachownica[row][col] += 1;
            }
        }
    }

    private Boolean isObstacle(int row, int col) {
        return !Character.isDigit(szachownica[row][col]);
    }

    public void calcAttack() {
        pionki.forEach((p) -> {
            attack(p);
        });
    }


}
