package com.rmi.game.board;

import com.google.common.base.Preconditions;
import com.rmi.game.Constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by novy on 18.04.15.
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Coordinates implements Serializable {

    private final int row;
    private final int columns;

    public static Coordinates of(int row, int column) {
        Preconditions.checkArgument(rowsInRange(row), "Invalid row value");
        Preconditions.checkArgument(columnsInRange(column), "Invalid column value");

        return new Coordinates(row, column);
    }

    private static boolean columnsInRange(int column) {
        return column >= 0 && column < Constants.BOARD_COLUMNS;
    }

    private static boolean rowsInRange(int rows) {
        return rows >= 0 && rows < Constants.BOARD_ROWS;
    }

}
