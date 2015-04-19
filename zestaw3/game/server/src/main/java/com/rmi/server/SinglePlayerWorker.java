package com.rmi.server;

import com.google.common.eventbus.EventBus;
import com.rmi.game.Player;
import com.rmi.game.RemoteExceptionConcurrentWrapper;

import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public class SinglePlayerWorker implements Runnable, StartingGameTrait {

    private final Player player;
    private final EventBus eventBus;

    public SinglePlayerWorker(Player player, EventBus eventBus) {
        this.player = player;
        this.eventBus = eventBus;
    }

    @Override
    public void run() {

        final Player enemy = new ComputerPlayer();
        tryToStartAGame(enemy);
        eventBus.post(
                new GameTookPlaceEvent(player, enemy)
        );
    }

    private void tryToStartAGame(Player enemy) {
        try {
            startGame(player, enemy);
        } catch (RemoteException e) {
            throw new RemoteExceptionConcurrentWrapper(e);
        }
    }
}
