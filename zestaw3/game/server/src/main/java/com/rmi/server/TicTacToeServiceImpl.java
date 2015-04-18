package com.rmi.server;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.rmi.game.GameType;
import com.rmi.game.Player;
import com.rmi.game.TicTacToeService;

import java.rmi.RemoteException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeServiceImpl implements TicTacToeService {

    final Set<Player> registeredPlayers = Sets.newConcurrentHashSet();
    final Queue<Player> awaitingForMultiplayer = Queues.newLinkedBlockingQueue();
    final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void register(Player player) throws RemoteException {
        // todo : check for existing
        registeredPlayers.add(player);
    }

    @Override
    public void unregister(Player player) throws RemoteException {
        registeredPlayers.remove(player);
    }

    @Override
    public void play(Player player, GameType gameType) throws RemoteException {
        if (gameType == GameType.SINGLE_PLAYER) {
            executorService.submit(new SinglePlayerWorker(player));
        } else {
            executorService.submit(new MultiPlayerWorker(player, awaitingForMultiplayer));
        }
    }

}
