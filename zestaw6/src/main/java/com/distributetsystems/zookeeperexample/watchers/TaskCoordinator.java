package com.distributetsystems.zookeeperexample.watchers;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by novy on 06.06.15.
 */
public class TaskCoordinator implements Watcher {

    private final String pathToWatch;
    private final String[] commandToExecute;
    private final Runtime executionContext;

    private Optional<Process> runningProcess = Optional.empty();

    public TaskCoordinator(
            String pathToWatch,
            String[] commandToExecute,
            Runtime executionContext) {

        Preconditions.checkArgument(
                !Strings.isNullOrEmpty(pathToWatch)
        );
        Preconditions.checkArgument(
                commandToExecute.length != 0
        );

        this.pathToWatch = pathToWatch;
        this.commandToExecute = commandToExecute;
        this.executionContext = executionContext;
    }

    public void process(WatchedEvent event) {

        if (!pathToWatch.equals(event.getPath())) {
            return;
        }

        final Event.EventType eventType = event.getType();

        switch (eventType) {
            case NodeCreated:
                executeJob();
                break;
            case NodeDeleted:
                abortJob();
                break;
            default:
                break;
        }

    }

    private void executeJob() {
        if (runningProcess.isPresent()) {
            return;
        }

        try {
            runningProcess = Optional.of(
                    executionContext.exec(commandToExecute)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abortJob() {
        if (!runningProcess.isPresent()) {
            return;
        }

        final Process process = runningProcess.get();
        process.destroy();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
