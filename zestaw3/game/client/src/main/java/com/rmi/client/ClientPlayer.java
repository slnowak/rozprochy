package com.rmi.client;

import com.rmi.game.Board;
import com.rmi.game.BoardCell;
import com.rmi.game.Player;

import java.util.Scanner;

/**
 * Created by novy on 18.04.15.
 */
public class ClientPlayer implements Player {

    private final String nick;
    private final BoardCell marker;

    public ClientPlayer(String nick, BoardCell marker) {
        this.nick = nick;
        this.marker = marker;
    }

    @Override
    public void doMove(Board board) {

        final Scanner stdIn = new Scanner(System.in);

        int x, y;
        do {
            System.out.println("Please type cell coords (x, y):\n");
            x = stdIn.nextInt();
            y = stdIn.nextInt();

        } while (!board.movePossible(x, y));

        board.markCell(x, y, marker);
    }

    @Override
    public String nick() {
        return nick;
    }
}
