package com.rmi.server;

import com.rmi.game.Player;
import com.rmi.game.board.BoardCell;
import com.rmi.game.board.Coordinates;
import com.rmi.game.board.IBoard;
import com.rmi.game.board.Movement;

import java.rmi.RemoteException;
import java.util.List;
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
    public void doMove(IBoard board) throws RemoteException {

        final List<Coordinates> unmarkedCells = board.unmarkedCells();
        Coordinates randomUnmarkedCell = unmarkedCells.get(
                randomGenerator.nextInt(unmarkedCells.size())
        );

        board.makeMovement(Movement.of(
                randomUnmarkedCell, marker
        ));
    }

    @Override
    public String nick() {
        return name;
    }
}
