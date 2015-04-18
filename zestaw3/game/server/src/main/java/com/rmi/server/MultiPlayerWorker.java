package com.rmi.server;

import com.rmi.game.Player;

import java.rmi.RemoteException;
import java.util.Queue;

/**
 * Created by novy on 19.04.15.
 */
public class MultiPlayerWorker implements Runnable, StartingGameTrait {

    private final Player player;
    private final Queue<Player> awaitingPlayers;

    public MultiPlayerWorker(Player player, Queue<Player> awaitingPlayers) {
        this.player = player;
        this.awaitingPlayers = awaitingPlayers;
    }

    @Override
    public void run() {
        Player enemy = null;

        synchronized (awaitingPlayers) {
            if (awaitingPlayers.isEmpty()) {
                awaitingPlayers.add(player);
                try {
                    player.onWaiting();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                enemy = awaitingPlayers.remove();
            }
        }

        if (enemy != null) {
            try {
                startGame(player, enemy);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
