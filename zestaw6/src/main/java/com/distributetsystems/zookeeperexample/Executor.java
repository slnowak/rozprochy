package com.distributetsystems.zookeeperexample;

import com.distributetsystems.zookeeperexample.watchers.ChildrenWatcher;
import com.distributetsystems.zookeeperexample.watchers.TaskCoordinator;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by novy on 06.06.15.
 */
public class Executor implements Runnable, Watcher {

    private final ZooKeeper zooKeeper;
    private final String znode;
    private TaskCoordinator taskCoordinator;
    private ChildrenWatcher childrenWatcher;

    public Executor(String znode,
                    ZooKeeper zooKeeper,
                    String commandToExecute[],
                    Runtime executionContext) throws KeeperException, IOException {

        this.znode = znode;
        this.zooKeeper = zooKeeper;
        zooKeeper.register(this);
        taskCoordinator = new TaskCoordinator(znode, commandToExecute, executionContext);
        childrenWatcher = new ChildrenWatcher(znode);
        zooKeeper.exists(znode, true, null, this);
    }

    public void run() {
        while (true) {
        }
    }

    public void process(WatchedEvent event) {
        taskCoordinator.process(event);
        zooKeeper.exists(znode, true, null, this);
        zooKeeper.getChildren(znode, true, childrenWatcher, this);

    }
}
