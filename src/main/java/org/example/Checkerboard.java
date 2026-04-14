package org.example;

import java.util.ArrayList;

public class Checkerboard {
    public char[][] szachownica;
    private int N;
    private ArrayList<Pionek> pionki = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();

    public Checkerboard(int N) {
        this.N = N;
        szachownica = new char[N + 2][N + 2];
        prepare();
    }

    public void prepare() {
        N += 2;
        szachownica[0][0] = '@';
        szachownica[0][N - 1] = '@';
        szachownica[N - 1][0] = '@';
        szachownica[N - 1][N - 1] = '@';
        for (int i = 0; i < N; i++) {
            szachownica[0][i] = '@';
            szachownica[i][0] = '@';
            szachownica[N - 1][i] = '@';
            szachownica[i][N - 1] = '@';
        }
        for (int i = 1; i < N - 1; i++) {
            for (int j = 1; j < N - 1; j++) {
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
            sz.append("\n");
        }
        return sz.toString();
    }

    public void placeK(int i, int j) {
        if (i >= N - 2 || j >= N - 2)
            throw new IndexOutOfBoundsException();
        i += 1;
        j += 1;
        szachownica[i][j] = 'K';
        Pionek p = new Pionek(i, j);
        pionki.add(p);
    }

    public void placeStar(int i, int j) {
        i += 1;
        j += 1;
        szachownica[i][j] = '*';
    }

    public void placeCannon(int i, int j, char c) {
        if (i >= N - 2 || j >= N - 2)
            throw new IndexOutOfBoundsException();
        i += 1;
        j += 1;
        szachownica[i][j] = c;
        Cannon armata = new Cannon(i, j, c);
        cannons.add(armata);
    }

    public void attack(Pionek p) {
        updateCell(p.i, p.j - 1); // lewo
        updateCell(p.i, p.j + 1); // prawo
        updateCell(p.i - 1, p.j); // gora
        updateCell(p.i + 1, p.j); // dol

        updateCell(p.i + 1, p.j + 1); // skos dol-prawo
        updateCell(p.i - 1, p.j - 1); // skos gora-lewo
        updateCell(p.i - 1, p.j + 1); // skos gora-prawo
        updateCell(p.i + 1, p.j - 1); // skos dol-lewo
    }

    public void funnyAttack(Pionek p) {
        // pionek po lewej
        if (szachownica[p.i][p.j - 1] == '@') {
            updateCell(p.i - 1, N - 2);
            updateCell(p.i, N - 2);
            updateCell(p.i + 1, N - 2);
        }
        // pionek po prawej
        if (szachownica[p.i][p.j + 1] == '@') {
            updateCell(p.i - 1, 1);
            updateCell(p.i, 1);
            updateCell(p.i + 1, 1);
        }
        // pionek na gorze
        if (szachownica[p.i - 1][p.j] == '@') {
            updateCell(N - 2, p.j - 1);
            updateCell(N - 2, p.j);
            updateCell(N - 2, p.j + 1);
        }
        // pionek na dole
        if (szachownica[p.i + 1][p.j] == '@') {
            updateCell(1, p.j - 1);
            updateCell(1, p.j);
            updateCell(1, p.j + 1);
        }
    }

    private void cannonAttack(Cannon c) {
        // pionek po lewej
        if (szachownica[c.i][c.j - 1] == 'K') {
            cannonShot(c);
        }
        // pionek po prawej
        if (szachownica[c.i][c.j + 1] == 'K') {
            cannonShot(c);
        }
        // pionek na gorze
        if (szachownica[c.i - 1][c.j] == 'K') {
            cannonShot(c);
        }
        // pionek na dole
        if (szachownica[c.i + 1][c.j] == 'K') {
            cannonShot(c);
        }
        // pionek lewo gora
        if (szachownica[c.i - 1][c.j + 1] == 'K') {
            cannonShot(c);
        }
        // pionek prawo gora
        if (szachownica[c.i + 1][c.j + 1] == 'K') {
            cannonShot(c);
        }
        // pionek lewo dol
        if (szachownica[c.i - 1][c.j - 1] == 'K') {
            cannonShot(c);
        }
        // pionek prawo dol
        if (szachownica[c.i + 1][c.j - 1] == 'K') {
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

    private void cannonShot(Cannon c) {
        switch (c.c) {
            case '|':
                // Strzał w górę
                for (int i = c.i - 1; i >= 0; i--) {
                    if (isObstacle(i, c.j))
                        break;
                    updateCell(i, c.j);
                }
                // Strzał w dół
                for (int i = c.i + 1; i < szachownica.length; i++) {
                    if (isObstacle(i, c.j))
                        break;
                    updateCell(i, c.j);
                }
                break;

            case '-':
                // Strzał w lewo
                for (int j = c.j - 1; j >= 0; j--) {
                    if (isObstacle(c.i, j))
                        break;
                    updateCell(c.i, j);
                }
                // Strzał w prawo
                for (int j = c.j + 1; j < szachownica[0].length; j++) {
                    if (isObstacle(c.i, j))
                        break;
                    updateCell(c.i, j);
                }
                break;

            case '\\':
                // Lewo-Góra
                for (int i = c.i - 1, j = c.j - 1; i >= 0 && j >= 0; i--, j--) {
                    if (isObstacle(i, j))
                        break;
                    updateCell(i, j);
                }
                // Prawo-Dół
                for (int i = c.i + 1, j = c.j + 1; i < N && j < N; i++, j++) {
                    if (isObstacle(i, j))
                        break;
                    updateCell(i, j);
                }
                break;

            case '/':
                // Prawo-Góra
                for (int i = c.i - 1, j = c.j + 1; i >= 0 && j < N; i--, j++) {
                    if (isObstacle(i, j))
                        break;
                    updateCell(i, j);
                }
                // Lewo-Dół
                for (int i = c.i + 1, j = c.j - 1; i < N && j >= 0; i++, j--) {
                    if (isObstacle(i, j))
                        break;
                    updateCell(i, j);
                }
                break;
        }
    }

    public void calcAttack() {
        pionki.forEach(this::attack);
        pionki.forEach(this::funnyAttack);
        cannons.forEach(this::cannonAttack);
    }

}
