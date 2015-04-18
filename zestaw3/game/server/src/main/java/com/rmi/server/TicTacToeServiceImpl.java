package com.rmi.server;

import com.rmi.game.Player;
import com.rmi.game.TicTacToeService;

import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeServiceImpl implements TicTacToeService {

    @Override
    public void register(Player player) throws RemoteException {
        System.out.println("no elo elo ziomneczku");
    }
}
