package com.jgroups.chat.consoleclient;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.messages.Message;

/**
 * Created by novy on 26.03.15.
 */
public class STDOutMessageWriter {

    private final String channelName;

    public STDOutMessageWriter(String channelName, EventBus eventBus) {
        this.channelName = channelName;
        eventBus.register(this);
    }

    @Subscribe
    public void on(Message message) {
        if (channelName.equals(message.channel())) {
            System.out.println("[" + channelName + "] " + message);
        }
    }
}
