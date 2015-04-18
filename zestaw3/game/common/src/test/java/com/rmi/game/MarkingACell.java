package com.rmi.game;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by novy on 18.04.15.
 */

public class MarkingACell {

    @Test
    public void shouldReturnFalseAskedAboutAlreadyMarkedCell() throws Exception {

        final Board objectUnderTest = new Board();
        objectUnderTest.markCell(0, 0, BoardCell.CROSS);

        assertThat(objectUnderTest.movePossible(0, 0)).isFalse();
    }

    @Test
    public void shouldReturnFalseAskedAboutMarkingPossibilityWhenSomeoneWon() throws Exception {

        final Board objectUnderTest = new Board();
        objectUnderTest.markCell(0, 0, BoardCell.CROSS);
        objectUnderTest.markCell(0, 1, BoardCell.CROSS);
        objectUnderTest.markCell(0, 2, BoardCell.CROSS);

        assertThat(objectUnderTest.movePossible(2, 2)).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToMarkAlreadyMarkedCell() throws Exception {

        final Board objectUnderTest = new Board();
        objectUnderTest.markCell(0, 0, BoardCell.CROSS);

        objectUnderTest.markCell(0, 0, BoardCell.NOUGHT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToMarkCellWhenSomeoneWon() throws Exception {

        final Board objectUnderTest = new Board();
        objectUnderTest.markCell(0, 0, BoardCell.CROSS);
        objectUnderTest.markCell(0, 1, BoardCell.CROSS);
        objectUnderTest.markCell(0, 2, BoardCell.CROSS);

        objectUnderTest.markCell(2, 2, BoardCell.NOUGHT);
    }
}