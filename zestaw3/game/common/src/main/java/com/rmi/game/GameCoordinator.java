package com.rmi.game;

import com.rmi.game.board.Board;

import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public class GameCoordinator {

    private final Player playerOne;
    private final Player playerTwo;
    private final Board board;

    public GameCoordinator(Player playerOne, Player playerTwo, Board board) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = board;
    }

    public void coordinate() throws RemoteException {
        final int maximumMoves = Constants.BOARD_ROWS * Constants.BOARD_COLUMNS;

        for (int move = 0; move <= maximumMoves; ++move) {
            final Player currentPlayer = determineCurrentPlayer(move);
            final Player waitingPlayer = determineWaitingPlayer(move);

            currentPlayer.doMove(board);

            currentPlayer.onBoardChanged(board.toString());
            waitingPlayer.onBoardChanged(board.toString());

            if (board.alreadyFinished()) {
                currentPlayer.onWin();
                waitingPlayer.onLoss();
                return;
            }
        }

        playerOne.onDraw();
        playerTwo.onDraw();

    }

    private Player determineCurrentPlayer(int move) {
        return move % 2 == 0 ? playerOne : playerTwo;
    }

    private Player determineWaitingPlayer(int move) {
        return move % 2 == 1 ? playerOne : playerTwo;
    }

}
