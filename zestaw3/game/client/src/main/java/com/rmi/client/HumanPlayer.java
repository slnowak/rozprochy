package com.rmi.client;

import com.rmi.game.Player;
import com.rmi.game.board.BoardCell;
import com.rmi.game.board.Coordinates;
import com.rmi.game.board.IBoard;
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
    public void doMove(IBoard board, BoardCell marker) throws RemoteException {

        final Scanner stdIn = new Scanner(System.in);

        int x, y;
        Coordinates coordinates;

        do {
            System.out.println("Please type cell coords (x, y):\n");
            x = stdIn.nextInt();
            y = stdIn.nextInt();
            coordinates = Coordinates.of(x, y);

        } while (!board.movePossible(coordinates));

        board.makeMovement(
                Movement.of(coordinates, marker)
        );
    }


    @Override
    public void onWin() throws RemoteException {
        System.out.println("Congratulations, you won!");
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
    public String nick() {
        return nick;
    }
}
