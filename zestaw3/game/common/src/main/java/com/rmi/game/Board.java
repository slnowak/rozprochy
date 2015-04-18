package com.rmi.game;

import com.google.common.base.Preconditions;

/**
 * Created by novy on 18.04.15.
 */
public class Board {

    public static final int COLUMNS = 3;
    public static final int ROWS = 3;

    private static final int CELLS_TO_WIN = 3;

    private final BoardCell[][] board = new BoardCell[COLUMNS][ROWS];
    private final WinningSequenceFinder winningSequenceFinder = new WinningSequenceFinder(
            board, COLUMNS, ROWS, CELLS_TO_WIN);

    public Board() {
        clearBoard();

    }

    private void clearBoard() {
        for (int i = 0; i < COLUMNS; ++i) {
            for (int j = 0; j < ROWS; ++j) {
                board[i][j] = BoardCell.EMPTY;
            }
        }
    }

    public boolean movePossible(int row, int column) {
        return coordsInRange(row, column) && !alreadyFinished() && board[row][column] == BoardCell.EMPTY;
    }

    private boolean coordsInRange(int row, int column) {
        return row >= 0 && row < ROWS && column >= 0 && column < COLUMNS;
    }

    public void markCell(int row, int column, BoardCell marker) {
        Preconditions.checkArgument(movePossible(row, column), "Move not possible!");
        Preconditions.checkArgument(marker != BoardCell.EMPTY, "Cannot mark with EMPTY marker");

        board[row][column] = marker;
    }

    public boolean alreadyFinished() {
        return eachCellMarked() || thereIsAWinner();
    }

    private boolean eachCellMarked() {
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                if (board[i][j] == BoardCell.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean thereIsAWinner() {
        return thereIsAWinningRow() || thereIsAWinningColumn() || thereIsAWinningLeftDiagonal()
                || thereIsAWinningRightDiagonal();
    }

    private boolean thereIsAWinningRow() {
        return winningSequenceFinder.thereIsAWinningRow();
    }

    private boolean thereIsAWinningRightDiagonal() {
        return winningSequenceFinder.thereIsAWinningRightDiagonal();
    }

    private boolean thereIsAWinningLeftDiagonal() {
        return winningSequenceFinder.thereIsAWinningLeftDiagonal();
    }

    private boolean thereIsAWinningColumn() {
        return winningSequenceFinder.thereIsAWinningColumn();
    }

    @Override
    public String toString() {
        String boardStringRepresentation = "";

        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                boardStringRepresentation += board[i][j] + " ";
            }
            boardStringRepresentation += "\n";
        }

        return boardStringRepresentation;
    }
}
