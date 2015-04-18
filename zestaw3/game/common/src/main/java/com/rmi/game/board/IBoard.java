package com.rmi.game.board;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by novy on 18.04.15.
 */
public interface IBoard extends Remote, Serializable {

    boolean movePossible(Coordinates coordinates) throws RemoteException;

    void makeMovement(Movement movement) throws RemoteException;

    boolean alreadyFinished() throws RemoteException;

    List<Coordinates> unmarkedCells() throws RemoteException;
}
