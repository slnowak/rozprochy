package com.rmi.server;

import com.rmi.game.TicTacToeService;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeServer {

    private static final String DEFAULT_RMI_SERVER_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_RMI_PORT = "1099";
    private static final String DEFAULT_RMI_SERVER_NAME = "server";

    public static void main(String[] args) throws Exception {
//        System.setProperty("java.rmi.server.hostname", "192.168.1.103");

        final String rmiServerAddress = parseRmiServerAddress(args);
        final String rmiServerPort = parseRmiPort(args);
        final String rmiServerName = parseRmiServerName(args);

        final TicTacToeService service = new TicTacToeServiceImpl();

        final Remote remoteService = UnicastRemoteObject.exportObject(service, 0);
        final String serviceBindPath = createRmiPath(rmiServerAddress, rmiServerPort, rmiServerName);

        Naming.rebind(serviceBindPath, remoteService);
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
}
