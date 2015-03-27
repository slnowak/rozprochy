package com.jgroups.chat.gui.tabbedchat.tab;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.messages.Message;
import com.jgroups.chat.operations.ChatOperationProtos.ChatMessage;
import org.jgroups.JChannel;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelTabPresenter extends AbstractPresenter<ChannelTab> {

    private final JChannel channel;

    public ChannelTabPresenter(EventBus globalEventBus, EventBus channelEventBus, JChannel channel) {
        globalEventBus.register(this);
        channelEventBus.register(this);
        this.channel = channel;
    }

    @Subscribe
    public void on(Message message) {
        if (message.channel().equals(view.channelName())) {
            view.addMessage(message.toString());
        }
    }

    public void handleMessageTyped(String messageContent) throws Exception {
        sendMessage(messageContent);
    }

    private void sendMessage(String messageContent) throws Exception {
        final ChatMessage chatMessage = ChatMessage
                .newBuilder()
                .setMessage(messageContent)
                .build();

        org.jgroups.Message message = new org.jgroups.Message(
                null, null, chatMessage.toByteArray()
        );

        channel.send(message);
    }
}
