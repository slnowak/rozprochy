package com.rmi.client;

import com.rmi.game.Constants;
import com.rmi.game.Player;
import com.rmi.game.board.Board;
import com.rmi.game.board.BoardCell;
import com.rmi.game.board.Coordinates;
import com.rmi.game.board.Movement;
import lombok.EqualsAndHashCode;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by novy on 18.04.15.
 */

@EqualsAndHashCode
public class HumanPlayer extends UnicastRemoteObject implements Player {

    private final String nick;

    public HumanPlayer(String nick) throws RemoteException {
        this.nick = nick;
    }

    @Override
    public void doMove(Board board, BoardCell marker) throws RemoteException {

        final Scanner stdIn = new Scanner(System.in);

        int x, y;
        Coordinates coordinates;

        do {
            System.out.println("Please type cell coords (row, column):\n");
            x = stdIn.nextInt();
            y = stdIn.nextInt();

        } while (!coordsValid(x, y) || !board.movePossible(Coordinates.of(x, y)));

        board.makeMovement(
                Movement.of(Coordinates.of(x, y), marker)
        );
    }

    private boolean coordsValid(int row, int column) {
        return row >= 0 && row < Constants.BOARD_ROWS && column >= 0 && column < Constants.BOARD_COLUMNS;
    }

    @Override
    public void onWin() throws RemoteException {
        System.out.println("Congratulations, you're the winner!");
    }

    @Override
    public void onLoss() throws RemoteException {
        System.out.println("You've lost!");
    }

    @Override
    public void onDraw() throws RemoteException {
        System.out.println("There is a draw!");
    }

    @Override
    public void onBoardChanged(String boardRepresentation) {
        System.out.println(boardRepresentation);
    }

    @Override
    public void onWaiting() throws RemoteException {
        System.out.println("Waiting for another player to connect... Stay tuned...");
    }

    @Override
    public void onGameStarted(String enemyNick) throws RemoteException {
        System.out.println("Started game with " + enemyNick);
    }

    @Override
    public void cleanup() throws RemoteException {
        UnicastRemoteObject.unexportObject(this, false);
    }

    @Override
    public String nick() {
        return nick;
    }
}
