package com.rmi.server;

import com.rmi.game.Player;
import com.rmi.game.board.BoardCell;
import com.rmi.game.board.Coordinates;
import com.rmi.game.board.IBoard;
import com.rmi.game.board.Movement;
import lombok.EqualsAndHashCode;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

/**
 * Created by novy on 18.04.15.
 */

@EqualsAndHashCode(of = "name")
public class ComputerPlayer implements Player {

    private static final String COMPUTER_NICK_PREFIX = "Computer";
    private final String name;
    private final Random randomGenerator;

    public ComputerPlayer() {
        randomGenerator = new Random();
        this.name = COMPUTER_NICK_PREFIX + randomGenerator.nextInt();
    }

    @Override
    public void doMove(IBoard board, BoardCell marker) throws RemoteException {

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
