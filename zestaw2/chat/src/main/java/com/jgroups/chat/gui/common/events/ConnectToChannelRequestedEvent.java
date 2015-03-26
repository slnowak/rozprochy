package com.jgroups.chat.gui.common.events;

/**
 * Created by novy on 26.03.15.
 */
public class ConnectToChannelRequestedEvent {

    private final String channel;

    public ConnectToChannelRequestedEvent(String channel) {
        this.channel = channel;
    }

    public String channel() {
        return channel;
    }
}
