package com.rmi.client;

import com.rmi.game.GameType;
import com.rmi.game.Player;
import com.rmi.game.TicTacToeService;

import java.rmi.Naming;
import java.util.Scanner;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeClient {

    private static final String DEFAULT_RMI_SERVER_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_RMI_PORT = "1099";
    private static final String DEFAULT_RMI_SERVER_NAME = "server";
    private static final String DEFAULT_NICK = "Player";

    public static void main(String[] args) throws Exception {
        final String rmiServerAddress = parseRmiServerAddress(args);
        final String rmiServerPort = parseRmiPort(args);
        final String rmiServerName = parseRmiServerName(args);
        final String nick = parseNick(args);

        final TicTacToeService serverService = (TicTacToeService) Naming.lookup(
                createRmiPath(rmiServerAddress, rmiServerPort, rmiServerName)
        );

        final Player player = new HumanPlayer(nick);
        serverService.register(player);

        System.out.println("Choose game type: 1 - Single, 2 - Multi");
        // todo: check
        int choose = new Scanner(System.in).nextInt();
        final GameType gameType = choose == 1 ? GameType.SINGLE_PLAYER : GameType.MULTI_PLAYER;
        serverService.play(player, gameType);
    }

    private static String createRmiPath(String rmiServerAddress, String rmiServerPort, String rmiServerName) {
        return "rmi://" + rmiServerAddress + ":" + rmiServerPort + "/" + rmiServerName;
    }


    private static String parseRmiServerAddress(String[] args) {
        return args.length > 0 ? args[0] : DEFAULT_RMI_SERVER_ADDRESS;
    }

    private static String parseRmiPort(String[] args) {
        return args.length > 1 ? args[1] : DEFAULT_RMI_PORT;
    }

    private static String parseRmiServerName(String[] args) {
        return args.length > 2 ? args[2] : DEFAULT_RMI_SERVER_NAME;
    }

    private static String parseNick(String[] args) {
        return args.length > 3 ? args[3] : DEFAULT_NICK;
    }
}
