package com.rmi.game;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public interface TicTacToeService extends Remote, Serializable {

    void register(Player player) throws RemoteException;

    void play(Player player, GameType gameType) throws RemoteException;

}
