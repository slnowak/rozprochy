package com.rmi.game;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by novy on 18.04.15.
 */
public class DeterminingWhetherGameIsFinished {

    @Test
    public void shouldReturnTrueWhenThereIsAWinningRow() throws Exception {

        final Board objectUnderTest = new Board();

        objectUnderTest.markCell(1, 0, BoardCell.CROSS);
        objectUnderTest.markCell(1, 1, BoardCell.CROSS);
        objectUnderTest.markCell(1, 2, BoardCell.CROSS);

        assertThat(objectUnderTest.alreadyFinished()).isTrue();

    }

    @Test
    public void shouldReturnTrueWhenThereIsAWinningColumn() throws Exception {

        final Board objectUnderTest = new Board();

        objectUnderTest.markCell(0, 1, BoardCell.NOUGHT);
        objectUnderTest.markCell(1, 1, BoardCell.NOUGHT);
        objectUnderTest.markCell(2, 1, BoardCell.NOUGHT);

        assertThat(objectUnderTest.alreadyFinished()).isTrue();
    }

    @Test
    public void shouldReturnTrueWhenThereIsAWinningLeftDiagonal() throws Exception {

        final Board objectUnderTest = new Board();

        objectUnderTest.markCell(0, 0, BoardCell.NOUGHT);
        objectUnderTest.markCell(1, 1, BoardCell.NOUGHT);
        objectUnderTest.markCell(2, 2, BoardCell.NOUGHT);

        assertThat(objectUnderTest.alreadyFinished()).isTrue();

    }

    @Test
    public void shouldReturnTrueWhenThereIsAWinningRightDiagonal() throws Exception {

        final Board objectUnderTest = new Board();

        objectUnderTest.markCell(0, 2, BoardCell.NOUGHT);
        objectUnderTest.markCell(1, 1, BoardCell.NOUGHT);
        objectUnderTest.markCell(2, 0, BoardCell.NOUGHT);

        assertThat(objectUnderTest.alreadyFinished()).isTrue();
    }


    @Test
    public void shouldReturnTrueWhenAllCellsMarkedAndThereIsADraw() throws Exception {

        final Board objectUnderTest = new Board();
        markAllCellsToBeADraw(objectUnderTest);

        assertThat(objectUnderTest.alreadyFinished()).isTrue();
    }

    @Test
    public void shouldReturnFalseOtherwise() throws Exception {

        final Board objectUnderTest = new Board();

        assertThat(objectUnderTest.alreadyFinished()).isFalse();
    }

    private void markAllCellsToBeADraw(Board board) {

        board.markCell(0, 0, BoardCell.CROSS);
        board.markCell(0, 1, BoardCell.NOUGHT);
        board.markCell(0, 2, BoardCell.NOUGHT);

        board.markCell(1, 0, BoardCell.NOUGHT);
        board.markCell(1, 1, BoardCell.CROSS);
        board.markCell(1, 2, BoardCell.CROSS);

        board.markCell(2, 0, BoardCell.CROSS);
        board.markCell(2, 1, BoardCell.CROSS);
        board.markCell(2, 2, BoardCell.NOUGHT);

    }

}
