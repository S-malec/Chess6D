package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ChekersTest {
    static Checkerboard ch;
    static int N;

    @BeforeAll
    public static void setUp() {
        N = 5;
        ch = new Checkerboard(N);
    }

    @Test
    public void correctSize() {
        String s = ch.Display();

        assertThat(s.replaceAll(" ", "").replaceAll("\n", "").length()).isEqualTo(N*N);
    }

    @Test
    public void ZeroedCheckerboard() {
        String s = ch.Display();

        assertThat(s.replaceAll(" ", "").replaceAll("\n", "")).matches("0+");
    }

    @Test
    public void EmptyCheckerboard() {
        String s = "";

        assertThat(s).isEmpty();

    }

}