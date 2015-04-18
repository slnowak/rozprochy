package com.rmi.server;

import com.rmi.game.Board;
import com.rmi.game.BoardCell;
import com.rmi.game.Player;

import java.util.Random;

/**
 * Created by novy on 18.04.15.
 */
public class ComputerPlayer implements Player {

    private static final String COMPUTER_NICK_PREFIX = "Computer";
    private final String name;
    private final BoardCell marker;
    private final Random randomGenerator;

    public ComputerPlayer(BoardCell marker) {
        this.name = COMPUTER_NICK_PREFIX + new Random().nextInt();
        this.marker = marker;
        randomGenerator = new Random();
    }

    @Override
    public void doMove(Board board) {

        int x, y;

        do {
            x = randomGenerator.nextInt(Board.COLUMNS);
            y = randomGenerator.nextInt(Board.ROWS);
        } while (!board.movePossible(x, y));

        board.markCell(x, y, marker);
    }

    @Override
    public String nick() {
        return name;
    }
}
