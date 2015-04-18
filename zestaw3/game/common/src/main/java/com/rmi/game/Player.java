package com.rmi.game;

/**
 * Created by novy on 18.04.15.
 */
public interface Player {

    void doMove(Board board);
    String nick();
}
