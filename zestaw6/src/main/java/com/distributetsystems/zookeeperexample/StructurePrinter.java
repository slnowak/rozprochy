package com.distributetsystems.zookeeperexample;

import com.google.common.base.Strings;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by novy on 07.06.15.
 */
public class StructurePrinter {

    private final ZooKeeper zooKeeper;

    public StructurePrinter(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void printStructure(String znode) throws KeeperException, InterruptedException {
        printStructure(znode, 0);
        System.out.println();
    }

    private void printStructure(String path, int indent) throws KeeperException, InterruptedException {

        System.out.print(
                Strings.repeat("\t", indent)
        );
        System.out.println(path);

        for (String child : zooKeeper.getChildren(path, false)) {
            printStructure(path + "/" + child, indent + 1);
        }
    }
}
