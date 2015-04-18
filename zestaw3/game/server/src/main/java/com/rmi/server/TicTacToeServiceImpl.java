package com.rmi.server;

import com.rmi.game.GameCoordinator;
import com.rmi.game.GameType;
import com.rmi.game.Player;
import com.rmi.game.TicTacToeService;
import com.rmi.game.board.Board;
import com.rmi.game.board.BoardCell;

import java.rmi.RemoteException;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeServiceImpl implements TicTacToeService {

    @Override
    public void register(Player player) throws RemoteException {
    }

    @Override
    public void play(Player player, GameType gameType) throws RemoteException {
        if (gameType == GameType.SINGLE_PLAYER) {
            final Player enemy = new ComputerPlayer(BoardCell.NOUGHT);
            new GameCoordinator(
                    player, enemy, new Board()
            ).coordinate();
        }

    }
}
