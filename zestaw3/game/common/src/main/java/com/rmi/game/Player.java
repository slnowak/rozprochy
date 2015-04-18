package com.rmi.game;

import com.rmi.game.board.IBoard;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public interface Player extends Remote, Serializable {

    void doMove(IBoard board) throws RemoteException;

    default void onWin() throws RemoteException {
    }

    default void onLoss() throws RemoteException {

    }

    default void onDraw() throws RemoteException {

    }

    default void onBoardChanged(String boardRepresentation) throws RemoteException{
    }

    String nick() throws RemoteException;
}
