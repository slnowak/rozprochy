package com.rmi.server;

import com.rmi.game.TicTacToeService;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

/**
 * Created by novy on 18.04.15.
 */
public class TicTacToeServer {

    private static final String PROPERTIES_FILE = "properties.conf";

    private String rmiServerAddress;
    private String rmiServerPort;
    private String rmiServerName;

    public TicTacToeServer() throws IOException {
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
        final TicTacToeService service = new TicTacToeServiceImpl();
        final TicTacToeServer server = new TicTacToeServer();

        final Remote remoteService = UnicastRemoteObject.exportObject(service, 0);
        final String serviceBindPath = "rmi://" + server.rmiServerAddress
                + ":" + server.rmiServerPort + "/" + server.rmiServerName;

        Naming.rebind(serviceBindPath, remoteService);
    }
}
