package com.distributetsystems.zookeeperexample;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by novy on 06.06.15.
 */
public class Main {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        if (args.length < 4) {
            System.err.println("USAGE: Main connectionString znode program [args ...]");
            System.exit(2);
        }
        String connectionString = args[0];
        String znode = args[1];
        String exec[] = new String[args.length - 2];
        System.arraycopy(args, 2, exec, 0, exec.length);
        final ZooKeeper zooKeeper = new ZooKeeper(connectionString, 3000, null);

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(
                new Executor(znode, zooKeeper, exec, Runtime.getRuntime())
        );

        final StructurePrinter structurePrinter = new StructurePrinter(zooKeeper);
        final Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What would like to do?\n[P] print structure\n[Q] quit");
            final String input = scanner.next();

            switch (input) {
                case "P":
                    structurePrinter.printStructure(znode);
                    break;
                case "Q":
                    executorService.shutdownNow();
                    executorService.awaitTermination(10, TimeUnit.SECONDS);
                    System.exit(0);
                default:
                    System.out.println("Incorrect choice");
            }
        }

    }
}
