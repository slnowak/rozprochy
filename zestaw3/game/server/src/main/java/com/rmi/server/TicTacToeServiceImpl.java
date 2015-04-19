package com.rmi.server;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.rmi.game.GameType;
import com.rmi.game.Player;
import com.rmi.game.TicTacToeService;
import com.rmi.game.UserAlreadyExistsException;

import java.rmi.RemoteException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeServiceImpl implements TicTacToeService {

    private static final int N_THREADS = 5;
    private final Set<String> registeredPlayerNicknames = Sets.newConcurrentHashSet();
    private final Queue<Player> awaitingForMultiplayer = Queues.newArrayDeque();
    private final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
    private final EventBus eventBus = new EventBus();

    public TicTacToeServiceImpl() {
        eventBus.register(this);
    }

    @Override
    public void register(Player player) throws RemoteException {
        synchronized (registeredPlayerNicknames) {
            if (registeredPlayerNicknames.contains(player.nick())) {
                throw new UserAlreadyExistsException();
            }
            registeredPlayerNicknames.add(player.nick());
        }
    }

    @Subscribe
    public void on(GameTookPlaceEvent event) throws RemoteException {
        final Player playerOne = event.getPlayerOne();
        final Player playerTwo = event.getPlayerTwo();

        synchronized (registeredPlayerNicknames) {
            registeredPlayerNicknames.remove(playerOne.nick());
            registeredPlayerNicknames.remove(playerTwo.nick());
        }

        playerOne.cleanup();
        playerTwo.cleanup();
    }

    @Override
    public void play(Player player, GameType gameType) throws RemoteException {
        if (gameType == GameType.SINGLE_PLAYER) {
            executorService.submit(new SinglePlayerWorker(player, eventBus));
        } else {
            executorService.submit(new MultiPlayerWorker(player, awaitingForMultiplayer, eventBus));
        }
    }

}
