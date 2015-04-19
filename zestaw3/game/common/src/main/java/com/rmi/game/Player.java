package com.rmi.game;

import com.rmi.game.board.Board;
import com.rmi.game.board.BoardCell;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public interface Player extends Remote, Serializable {

    void doMove(Board board, BoardCell marker) throws RemoteException;

    default void onWin() throws RemoteException {
    }

    default void onLoss() throws RemoteException {

    }

    default void onDraw() throws RemoteException {

    }

    default void onWaiting() throws RemoteException {

    }

    default void onGameStarted(String enemyNick) throws RemoteException {

    }

    default void onBoardChanged(String boardRepresentation) throws RemoteException {
    }

    default void cleanup() throws RemoteException {

    }

    String nick() throws RemoteException;
}
