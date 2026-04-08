package org.example;

import java.util.ArrayList;

public class Checkerboard {
    public char[][] szachownica;
    private int N;
    private ArrayList<Pionek> pionki = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();

    public Checkerboard(int N) {
        this.N = N;
        szachownica = new char[N+2][N+2];
        prepare();
    }

    public void prepare() {
        N+=2;
        szachownica[0][0] = '@';
        szachownica[0][N-1] = '@';
        szachownica[N-1][0] = '@';
        szachownica[N-1][N-1] = '@';
        for (int i = 0; i < N; i++) {
            szachownica[0][i] = '@';
            szachownica[i][0] = '@';
            szachownica[N-1][i] = '@';
            szachownica[i][N-1] = '@';
        }
        for (int i = 1; i < N-1; i++) {
            for (int j = 1; j < N-1; j++) {
                szachownica[i][j] = '0';
            }
        }
    }

    public String display() {
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
        if (i>=N-2 || j>=N-2) throw new IndexOutOfBoundsException();
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

    public void placeCannon(int i, int j, char c) {
        if (i>=N-2 || j>=N-2) throw new IndexOutOfBoundsException();
        i+=1;
        j+=1;
        szachownica[i][j] = c;
        Cannon armata = new Cannon(i, j , c);
        cannons.add(armata);
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

    public void funnyAttack(Pionek p) {
        // pionek po lewej
        if (String.valueOf(szachownica[p.i][p.j-1]).equals("@")) {
            updateCell(p.i-1, N-2);
            updateCell(p.i, N-2);
            updateCell(p.i+1, N-2);
        }
        // pionek po prawej
        if (String.valueOf(szachownica[p.i][p.j+1]).equals("@")) {
            updateCell(p.i-1, 1);
            updateCell(p.i, 1);
            updateCell(p.i+1, 1);
        }
        // pionek na gorze
        if (String.valueOf(szachownica[p.i-1][p.j]).equals("@")) {
            updateCell(N-2, p.j-1);
            updateCell(N-2, p.j);
            updateCell(N-2, p.j+1);
        }
        // pionek na dole
        if (String.valueOf(szachownica[p.i+1][p.j]).equals("@")) {
            updateCell(1, p.j-1);
            updateCell(1, p.j);
            updateCell(1, p.j+1);
        }
    }

    private void cannonAttack(Cannon c) {
        // pionek po lewej
        if (String.valueOf(szachownica[c.i][c.j-1]).equals("K")) {
            cannonShot(c);
        }
        // pionek po prawej
        if (String.valueOf(szachownica[c.i][c.j+1]).equals("K")) {
            cannonShot(c);
        }
        // pionek na gorze
        if (String.valueOf(szachownica[c.i-1][c.j]).equals("K")) {
            cannonShot(c);
        }
        // pionek na dole
        if (String.valueOf(szachownica[c.i+1][c.j]).equals("K")) {
            cannonShot(c);
        }
        // pionek lewo gora
        if (String.valueOf(szachownica[c.i-1][c.j+1]).equals("K")) {
            cannonShot(c);
        }
        // pionek prawo gora
        if (String.valueOf(szachownica[c.i+1][c.j+1]).equals("K")) {
            cannonShot(c);
        }
        // pionek lewo dol
        if (String.valueOf(szachownica[c.i-1][c.j-1]).equals("K")) {
            cannonShot(c);
        }
        // pionek prawo dol
        if (String.valueOf(szachownica[c.i+1][c.j-1]).equals("K")) {
            cannonShot(c);
        }
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

    private int isCanon(int row, int col) {
        return switch (szachownica[row][col]) {
            case '/' -> 1;
            case '|' -> 2;
            case '\\' -> 3;
            case '-' -> 4;
            default -> 0;
        };
    }

    private void cannonShot(Cannon c){
        switch (c.c) {
            case '|':
                for (int i = c.i; i >= 0; i--) {
                    updateCell(i, c.j);
                }
                for (int i = c.i; i < N; i++) {
                    updateCell(i, c.j);
                }
            case '-':
                for (int j = c.j; j >= 0; j--) {
                    updateCell(c.i, j);
                }
                for (int j = c.j; j < N; j++) {
                    updateCell(c.i, j);
                }
            case '\\':
                for (int j = c.j; j >= 0; j--) {
                    for (int i = c.i; i >= 0; i--) {
                        if (c.i - i == c.j - j) {
                            updateCell(i, j);
                        }
                    }
                }
                for (int j = c.j; j < N; j++) {
                    for (int i = c.i; i < N; i++) {
                        if (c.i - i == c.j - j) {
                            updateCell(i, j);
                        }
                    }
                }

        }
    }

    public void calcAttack() {
        pionki.forEach(this::attack);
        pionki.forEach(this::funnyAttack);
        cannons.forEach(this::cannonAttack);
    }


}
