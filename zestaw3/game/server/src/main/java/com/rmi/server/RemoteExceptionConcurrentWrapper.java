package com.rmi.server;

import java.rmi.RemoteException;

/**
 * Created by novy on 19.04.15.
 */
public class RemoteExceptionConcurrentWrapper extends RuntimeException {
    public RemoteExceptionConcurrentWrapper(RemoteException cause) {
        super(cause);
    }
}
