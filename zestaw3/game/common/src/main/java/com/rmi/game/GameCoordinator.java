package com.rmi.game;

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

    public void coordinate() {
        final int maximumMoves = Board.ROWS * Board.ROWS;

        System.out.println(board);

        for (int move = 0; move <= maximumMoves; ++move) {
            final Player currentPlayer = determineCurrentPlayer(move);
            currentPlayer.doMove(board);

            System.out.println(board);

            if (board.alreadyFinished()) {
                System.out.println("Player " + currentPlayer.nick() + " won");
                return;
            }
        }

        System.out.println("There was a draw!");

    }

    private Player determineCurrentPlayer(int move) {
        return move % 2 == 0 ? playerOne : playerTwo;
    }

}
