package com.distributetsystems.zookeeperexample;

/**
 * Created by novy on 06.06.15.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err
                    .println("USAGE: Executor hostPort znode filename program [args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String znode = args[1];
        String filename = args[2];
        String exec[] = new String[args.length - 3];
        System.arraycopy(args, 3, exec, 0, exec.length);
        try {

            new Thread(
                    new MyExecutor(hostPort, znode, exec, Runtime.getRuntime())
            ).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
