package com.rmi.game.board;

/**
 * Created by novy on 18.04.15.
 */
public enum BoardCell {

    NOUGHT("O"),
    CROSS("X"),
    EMPTY("-");

    private final String representation;

    BoardCell(String representation) {
        this.representation = representation;
    }

    public String representation() {
        return representation;
    }

    @Override
    public String toString() {
        return representation();
    }
}
