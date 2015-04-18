package com.rmi.game;

/**
 * Created by novy on 18.04.15.
 */
class WinningSequenceFinder {

    private final BoardCell[][] board;
    private final int columns;
    private final int rows;
    private final int sequenceLength;

    public WinningSequenceFinder(BoardCell[][] board, int columns, int rows, int sequenceLength) {
        this.board = board;
        this.columns = columns;
        this.rows = rows;
        this.sequenceLength = sequenceLength;
    }

    public boolean thereIsAWinningRow() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j <= columns - sequenceLength; ++j) {
                final BoardCell cellToCompare = board[j][i];
                boolean foundWinningSequence = true;
                if (cellToCompare == BoardCell.EMPTY)
                    continue;

                for (int k = 1; k < sequenceLength; ++k) {
                    if (board[j + k][i] != cellToCompare) {
                        foundWinningSequence = false;
                        break;
                    }
                }

                if (foundWinningSequence) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean thereIsAWinningColumn() {
        for (int i = 0; i < columns; ++i) {
            for (int j = 0; j <= rows - sequenceLength; ++j) {
                final BoardCell cellToCompare = board[i][j];
                boolean foundWinningSequence = true;
                if (cellToCompare == BoardCell.EMPTY)
                    continue;

                for (int k = 1; k < sequenceLength; ++k) {
                    if (board[i][j + k] != cellToCompare) {
                        foundWinningSequence = false;
                        break;
                    }
                }

                if (foundWinningSequence) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean thereIsAWinningLeftDiagonal() {
        for (int i = 0; i <= columns - sequenceLength; ++i) {
            for (int j = 0; j <= rows - sequenceLength; ++j) {
                final BoardCell cellToCompare = board[i][j];
                boolean foundWinningSequence = true;
                if (cellToCompare == BoardCell.EMPTY)
                    continue;

                for (int k = 1; k < sequenceLength; ++k) {
                    if (board[i + k][j + k] != cellToCompare) {
                        foundWinningSequence = false;
                        break;
                    }
                }

                if (foundWinningSequence) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean thereIsAWinningRightDiagonal() {
        for (int i = columns - 1; i >= sequenceLength -1; --i) {
            for (int j = 0; j <= rows - sequenceLength; ++j) {
                final BoardCell cellToCompare = board[i][j];
                boolean foundWinningSequence = true;
                if (cellToCompare == BoardCell.EMPTY)
                    continue;

                for (int k = 1; k < sequenceLength; ++k) {
                    if (board[i - k][j + k] != cellToCompare) {
                        foundWinningSequence = false;
                        break;
                    }
                }

                if (foundWinningSequence) {
                    return true;
                }
            }
        }

        return false;
    }

}
