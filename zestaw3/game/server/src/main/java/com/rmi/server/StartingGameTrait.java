package com.rmi.server;

import com.rmi.game.GameCoordinator;
import com.rmi.game.Player;
import com.rmi.game.board.Board;

import java.rmi.RemoteException;

/**
 * Created by novy on 19.04.15.
 */
public interface StartingGameTrait {

    default void startGame(Player playerOne, Player playerTwo) throws RemoteException {
        new GameCoordinator(playerOne, playerTwo, new Board())
                .coordinate();
    }
}
