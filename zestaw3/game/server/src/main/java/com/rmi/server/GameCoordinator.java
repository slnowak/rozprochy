package com.rmi.server;

import com.google.common.collect.ImmutableMap;
import com.rmi.game.Constants;
import com.rmi.game.Player;
import com.rmi.game.board.Board;
import com.rmi.game.board.BoardCell;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * Created by novy on 18.04.15.
 */
public class GameCoordinator {

    private final Player playerOne;
    private final Player playerTwo;
    private final Board board;

    private final Map<Player, BoardCell> markers;


    public GameCoordinator(Player playerOne, Player playerTwo, Board board) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = board;

        markers = ImmutableMap.of(
                playerOne, BoardCell.CROSS,
                playerTwo, BoardCell.NOUGHT
        );

    }

    public void coordinate() throws RemoteException {
        final int maximumMoves = Constants.BOARD_ROWS * Constants.BOARD_COLUMNS;

        playerOne.onGameStarted(playerTwo.nick());
        playerTwo.onGameStarted(playerOne.nick());

        for (int move = 0; move < maximumMoves; ++move) {
            final Player currentPlayer = determineCurrentPlayer(move);
            final Player waitingPlayer = determineWaitingPlayer(move);

            currentPlayer.doMove(board, markers.get(currentPlayer));

            currentPlayer.onBoardChanged(board.toString());
            waitingPlayer.onBoardChanged(board.toString());

            if (board.thereIsAWinner()) {
                currentPlayer.onWin();
                waitingPlayer.onLoss();
                return;
            }
        }

        playerOne.onDraw();
        playerTwo.onDraw();

        UnicastRemoteObject.unexportObject(board, false);
    }

    private Player determineCurrentPlayer(int move) {
        return move % 2 == 0 ? playerOne : playerTwo;
    }

    private Player determineWaitingPlayer(int move) {
        return move % 2 == 1 ? playerOne : playerTwo;
    }

}
