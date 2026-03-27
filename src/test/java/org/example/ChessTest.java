package org.example;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ChessTest {
    static Checkerboard ch;
    static int N;

    @BeforeEach
    public void setUp() {
        N = 5;
        ch = new Checkerboard(N);
    }

    @AfterEach
    public void tearDown() {
        ch = null;
    }

    @Test
    public void correctSize() {
        String s = ch.Display();

        assertThat(s.replaceAll(" ", "").replaceAll("\n", "").length()).isEqualTo(N*N);
    }

    @Test
    public void zeroedCheckerboard() {
        String s = ch.Display();

        assertThat(s.replaceAll(" ", "").replaceAll("\n", "")).matches("0+");
    }

    @Test
    public void ifKingsStandNextToEachOtherKingsValueDosentChange() {
        ch.placeK(1,1);
        ch.placeK(1,2);

        ch.calcAttack();

        assertThat(ch.szachownica[1][2]).isEqualTo('K');
        assertThat(ch.szachownica[1][1]).isEqualTo('K');
    }

    @Test
    public void ifKingsStandNextToEachOtherSameAttackedFieldIsIncreased() {
        ch.placeK(1,1);
        ch.placeK(1,2);

        ch.calcAttack();

        assertThat(ch.szachownica[0][1]).isEqualTo('2');
        assertThat(ch.szachownica[0][2]).isEqualTo('2');
        assertThat(ch.szachownica[2][1]).isEqualTo('2');
        assertThat(ch.szachownica[2][2]).isEqualTo('2');
    }

    @Test
    public void shouldNotThrowExceptionWhenKingIsInCorner() {
        ch.placeK(0, 0);

        assertThatCode(() -> {
            ch.calcAttack();
        }).doesNotThrowAnyException();

        assertThat(ch.szachownica[0][1]).isEqualTo('1'); // prawo
        assertThat(ch.szachownica[1][0]).isEqualTo('1'); // dol
        assertThat(ch.szachownica[1][1]).isEqualTo('1'); // skos
    }

    @Test
    public void shouldNotAttackAnObstacle() {
        ch.placeK(0, 0);
        ch.placeStar(0, 1);

        ch.calcAttack();

        assertThat(ch.szachownica[0][1]).isEqualTo('*');
    }
}