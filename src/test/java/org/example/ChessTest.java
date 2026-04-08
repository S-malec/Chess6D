package org.example;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String s = ch.display();

        System.out.println(s.replaceAll(" ", "").replaceAll("\n", "").replaceAll("@", ""));
        assertThat(s.replaceAll(" ", "").replaceAll("\n", "").replaceAll("@", "").length()).isEqualTo((N)*(N));
    }

    @Test
    public void zeroedCheckerboard() {
        String s = ch.display();

        assertThat(s.replaceAll(" ", "").replaceAll("\n", "").replaceAll("@", "")).matches("0+");
    }

    @Test
    public void ifKingsStandNextToEachOtherKingsValueDosentChange() {
        ch.placeK(1,1);
        ch.placeK(1,2);

        ch.calcAttack();

        assertThat(ch.szachownica[2][3]).isEqualTo('K');
        assertThat(ch.szachownica[2][2]).isEqualTo('K');
    }

    @Test
    public void ifKingsStandNextToEachOtherSameAttackedFieldIsIncreased() {
        ch.placeK(1,1);
        ch.placeK(1,2);

        ch.calcAttack();

        assertThat(ch.szachownica[1][2]).isEqualTo('2');
        assertThat(ch.szachownica[1][3]).isEqualTo('2');
        assertThat(ch.szachownica[3][2]).isEqualTo('2');
        assertThat(ch.szachownica[3][3]).isEqualTo('2');
    }

    @Test
    public void shouldNotThrowExceptionWhenKingIsInCorner() {
        ch.placeK(0, 0);

        assertThatCode(() -> {
            ch.calcAttack();
        }).doesNotThrowAnyException();

        assertThat(ch.szachownica[1][2]).isEqualTo('1'); // prawo
        assertThat(ch.szachownica[2][1]).isEqualTo('1'); // dol
        assertThat(ch.szachownica[2][2]).isEqualTo('1'); // skos
    }

    @Test
    public void shouldNotAttackAnObstacle() {
        ch.placeK(0, 0);
        ch.placeStar(0, 1);

        ch.calcAttack();

        assertThat(ch.szachownica[1][2]).isEqualTo('*');
    }

    @Test
    public void shouldNotAttackBorder(){
        ch.placeK(0, 0);
        ch.calcAttack();

        assertThat(ch.szachownica[0][0]).isEqualTo('@');
        assertThat(ch.szachownica[0][1]).isEqualTo('@');
        assertThat(ch.szachownica[0][2]).isEqualTo('@');
        assertThat(ch.szachownica[1][0]).isEqualTo('@');
        assertThat(ch.szachownica[2][0]).isEqualTo('@');
    }

    @Test
    public void placingOnBorderThrowsIllegalArgumentException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            ch.placeK(5, 0);
            ch.calcAttack();
        });
}
}