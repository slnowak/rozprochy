package com.rmi.game.board;

/**
 * Created by novy on 19.04.15.
 */
class MovementFactory {

    public static Movement movementOf(int row, int column, BoardCell marker) {
        return Movement.of(
                Coordinates.of(row, column), marker
        );
    }
}
