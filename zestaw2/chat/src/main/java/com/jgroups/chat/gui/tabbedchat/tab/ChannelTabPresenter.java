package com.jgroups.chat.gui.tabbedchat.tab;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.messages.Message;
import org.jgroups.JChannel;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelTabPresenter extends AbstractPresenter<ChannelTab> {

    public ChannelTabPresenter(EventBus globalEventBus, EventBus channelEventBus, JChannel jchannel) {
        globalEventBus.register(this);
        channelEventBus.register(this);
    }

    @Subscribe
    public void on(Message message) {
        if (message.channel().equals(view.channelName())) {
            view.addMessage(message.toString());
        }
    }
}
