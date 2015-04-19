package com.rmi.server;

import com.rmi.game.Player;

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
        tryToStartAGame(enemy);
    }

    private void tryToStartAGame(Player enemy) {
        try {
            startGame(player, enemy);
        } catch (RemoteException e) {
            throw new RemoteExceptionConcurrentWrapper(e);
        }
    }
}
