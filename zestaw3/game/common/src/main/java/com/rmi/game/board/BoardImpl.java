package com.rmi.game.board;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static com.rmi.game.Constants.*;

/**
 * Created by novy on 18.04.15.
 */
public class BoardImpl extends UnicastRemoteObject implements Board {

    private final BoardCell[][] board = new BoardCell[BOARD_COLUMNS][BOARD_ROWS];
    private final WinningSequenceFinder winningSequenceFinder = new WinningSequenceFinder(
            board, BOARD_COLUMNS, BOARD_ROWS, CELLS_TO_WIN
    );

    public BoardImpl() throws RemoteException {
        clearBoard();
    }

    private void clearBoard() {
        for (int i = 0; i < BOARD_COLUMNS; ++i) {
            for (int j = 0; j < BOARD_ROWS; ++j) {
                board[i][j] = BoardCell.EMPTY;
            }
        }
    }

    @Override
    public boolean movePossible(Coordinates coordinates) {
        return !eachCellMarked() && !thereIsAWinner() && unmarkedCells().contains(coordinates);
    }

    @Override
    public void makeMovement(Movement aMovement) {
        Preconditions.checkArgument(movePossible(aMovement.getCoordinates()), "Move not possible!");

        board[aMovement.getCoordinates().getRow()][aMovement.getCoordinates().getColumns()]
                = aMovement.getMarker();
    }


    @Override
    public boolean thereIsAWinner() {
        return thereIsAWinningRow() || thereIsAWinningColumn() || thereIsAWinningLeftDiagonal()
                || thereIsAWinningRightDiagonal();
    }

    @Override
    public List<Coordinates> unmarkedCells() {
        List<Coordinates> unmarkedCells = Lists.newArrayList();
        for (int i = 0; i < BOARD_ROWS; ++i) {
            for (int j = 0; j < BOARD_COLUMNS; ++j) {
                if (board[i][j] == BoardCell.EMPTY) {
                    unmarkedCells.add(
                            Coordinates.of(i, j)
                    );
                }
            }
        }

        return unmarkedCells;
    }

    private boolean eachCellMarked() {
        return unmarkedCells().isEmpty();
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

        for (int i = 0; i < BOARD_ROWS; ++i) {
            for (int j = 0; j < BOARD_COLUMNS; ++j) {
                boardStringRepresentation += board[i][j] + " ";
            }
            boardStringRepresentation += "\n";
        }

        return boardStringRepresentation;
    }
}
