package com.rmi.game;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.*;

/**
 * Created by novy on 18.04.15.
 */

@RunWith(Parameterized.class)
public class AgainstCoordinatesOutOfBoard {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, -1, BoardCell.CROSS},
                {0, Board.COLUMNS, BoardCell.CROSS},
                {-1, 0, BoardCell.CROSS},
                {Board.ROWS, 0, BoardCell.CROSS}
        });
    }

    private int row;
    private int column;
    private BoardCell marker;

    public AgainstCoordinatesOutOfBoard(int row, int column, BoardCell marker) {
        this.row = row;
        this.column = column;
        this.marker = marker;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToMarkCellOutsideBoard() throws Exception {

        final Board objectUnderTest = new Board();

        objectUnderTest.markCell(row, column, marker);

    }

}
