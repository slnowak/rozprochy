package com.rmi.client;

import com.rmi.game.Player;
import com.rmi.game.TicTacToeService;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.Properties;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeClient {

    public static final String PROPERTIES_FILE = "properties.conf";

    private TicTacToeService ticTacToeService;
    private Player player;
    private String rmiServerAddress;
    private String rmiServerPort;
    private String rmiServerName;

    public TicTacToeClient(String nick) throws IOException {
        readProperties();
    }

    public void readProperties() throws IOException {
        Properties properties = new Properties();
//        properties.load(new FileInputStream(PROPERTIES_FILE));
        rmiServerAddress = properties.getProperty("rmiServerAddress", "127.0.0.1");
        rmiServerPort = properties.getProperty("rmiServerPort", "1099");
        rmiServerName = properties.getProperty("rmiServerName", "server");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: client <nick>");
            return;
        }

        final TicTacToeClient client = new TicTacToeClient(args[0]);
        final TicTacToeService serverService = (TicTacToeService) Naming.lookup("rmi://" + client.rmiServerAddress
                + ":" + client.rmiServerPort + "/" + client.rmiServerName);

        serverService.register(null);
    }
}