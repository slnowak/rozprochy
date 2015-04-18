package com.rmi.server;

import com.rmi.game.Player;
import com.rmi.game.board.BoardCell;

import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public class SinglePlayerWorker implements Runnable, StartingGameTrait {

    private final Player player;

    public SinglePlayerWorker(Player player) {
        this.player = player;
    }

    @Override
    public void run() {

        final Player enemy = new ComputerPlayer();
        try {
            startGame(player, enemy);
            // fix
        } catch (RemoteException e) {
            throw new RuntimeException();
        }
    }
}
