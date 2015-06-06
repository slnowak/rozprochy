package com.distributetsystems.zookeeperexample;

import com.distributetsystems.zookeeperexample.watchers.TaskCoordinator;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by novy on 06.06.15.
 */
public class MyExecutor implements Runnable, Watcher {

    private final ZooKeeper zooKeeper;
    private final String znode;
    private TaskCoordinator taskCoordinator;

    public MyExecutor(String connectString,
                      String znode,
                      String commandToExecute[],
                      Runtime executionContext) throws KeeperException, IOException {
        this.znode = znode;

        zooKeeper = new ZooKeeper(connectString, 3000, this);
        taskCoordinator = new TaskCoordinator(zooKeeper, znode, commandToExecute, executionContext);
        zooKeeper.exists(znode, true, null, this);
    }

    public void run() {
        while (true) {
        }
    }

    public void process(WatchedEvent event) {
        taskCoordinator.process(event);
        zooKeeper.exists(znode, true, null, this);
    }
}
